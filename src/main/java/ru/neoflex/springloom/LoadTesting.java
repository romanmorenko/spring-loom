package ru.neoflex.springloom;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.neoflex.springloom.dto.BookDTO;

import java.net.URI;
import java.net.http.HttpClient;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Data
public class LoadTesting {

    private static final int BOOKS_COUNT = 1000;
    private static final int SLEEPS_COUNT = 1000;
    private static final boolean IS_VIRTUAL = true;
    private HttpClient httpClient = HttpClient.newBuilder()
            .executor(Executors.newVirtualThreadExecutor())
            .connectTimeout(Duration.ofMillis(10000)).build();
    private AtomicInteger errorCounts = new AtomicInteger(0);
    private AtomicInteger successCounts = new AtomicInteger(0);


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LoadTesting loadTesting = new LoadTesting();
        log.info("Start clean db");
        loadTesting.clear().get();
        log.info("Database is clean");
        log.info("Start add books in {} threads", IS_VIRTUAL ? "virtual" : "OS");
        double startTime = System.nanoTime() / 1000_000_000.0;
        CompletableFuture.allOf(loadTesting.addBooks(IS_VIRTUAL)
                .toArray(new CompletableFuture[BOOKS_COUNT])).join();
        double completeTime = System.nanoTime() / 1000_000_000.0;
        log.info("Complete with {} s, success {} and errors {} ", completeTime - startTime,
                loadTesting.successCounts, loadTesting.errorCounts);
    }


    public CompletableFuture<String> clear() {
        errorCounts.set(0);
        successCounts.set(0);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8083/book"))
                .header("Content-Type", "application/json; charset=UTF-8")
                .DELETE()
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(it -> it.body())
                .exceptionally(e -> {
                    log.error("Error clear ", e);
                    System.exit(-1);
                    return null;
                });
    }

    public List<CompletableFuture<String>> addBooks(boolean isVirtual) {
        return IntStream.range(0, BOOKS_COUNT).mapToObj(it -> {
            String bookName = "Book";
            String port = isVirtual ? "8083" : "8081";
            ObjectMapper mapper = new ObjectMapper();
            BookDTO bookDTO = new BookDTO(bookName + it);
            String requestBody = null;
            try {
                requestBody = mapper.writeValueAsString(bookDTO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                System.exit(-1);
            }
            HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(requestBody);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + port + "/book"))
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .POST(bodyPublisher)
                    .build();
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply((response) -> {
                        String body = response.body();
                        log.info("Response body for add book request: {}", response.body());
                        successCounts.incrementAndGet();
                        return body;
                    }).exceptionally(e -> {
                        log.error("Error for add book request", e);
                        errorCounts.incrementAndGet();
                        return e.getMessage();
                    });
        }).collect(Collectors.toList());
    }

    public List<CompletableFuture<String>> sleep() {
        return IntStream.range(0, SLEEPS_COUNT).mapToObj(it -> {
            Random random = new Random(System.currentTimeMillis());
            Long time = random.nextLong(10, 100);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8083/sleep/" + time))
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .GET().build();
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply((response) -> {
                        log.info("Response body for sleep request: {}", response.body());
                        return response.body();
                    })
                    .exceptionally(e -> {
                        log.error("Error for sleep request", e);
                        return e.getMessage();
                    });
        }).collect(Collectors.toList());
    }

}
