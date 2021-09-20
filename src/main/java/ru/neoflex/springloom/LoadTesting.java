package ru.neoflex.springloom;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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

    private static final int BOOKS_COUNT = 1600;
    private static final int SLEEPS_COUNT = 1600;
    private static final int SLEEPS_MIN_TIME_MS = 100;
    private static final int SLEEPS_MAX_TIME_MS = 1000;
    private static final boolean IS_VIRTUAL = true;
    private static final boolean LOG_RESPONSES = false;
    private static final boolean LOG_ERRORS = false;
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=UTF-8";

    private HttpClient httpClient = HttpClient.newBuilder()
            .executor(Executors.newVirtualThreadExecutor())
            .connectTimeout(Duration.ofMillis(10000)).build();
    private AtomicInteger errorCounts = new AtomicInteger();
    private AtomicInteger successCounts = new AtomicInteger();
    double startTime;


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LoadTesting loadTesting = new LoadTesting();
        loadTesting.clear().get();
        CompletableFuture.allOf(loadTesting.addBooks()
                .toArray(new CompletableFuture[BOOKS_COUNT])).join();
        loadTesting.logResult(loadTesting);
        CompletableFuture.allOf(loadTesting.sleep()
                        .toArray(new CompletableFuture[SLEEPS_COUNT]))
                .join();
        loadTesting.logResult(loadTesting);
    }

    private void logResult(LoadTesting loadTesting) {
        log.info("Complete with {} s, success {} and errors {} ", (System.nanoTime() / 1000_000_000.0) - startTime,
                loadTesting.successCounts, loadTesting.errorCounts);
    }


    public CompletableFuture<String> clear() {
        log.info("Start clean db");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8083/book"))
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                .DELETE()
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .exceptionally(e -> {
                    log.error("Error clear ", e);
                    System.exit(-1);
                    return null;
                }).whenComplete((res, ex) -> log.info("Database is clean"));
    }


    public List<CompletableFuture<String>> addBooks() {
        log.info("Start add books in {} threads", IS_VIRTUAL ? "virtual" : "OS native");
        startMeasure();
        return IntStream.range(0, BOOKS_COUNT).mapToObj(it -> {
            String bookName = "Book";
            String port = getPort();
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
                    .uri(URI.create(String.format("http://localhost:%s/book", port)))
                    .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                    .POST(bodyPublisher)
                    .build();
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        String body = response.body();
                        if (LOG_RESPONSES) log.info("Response body for add book request: {}", response.body());
                        successCounts.incrementAndGet();
                        return body;
                    }).exceptionally(e -> {
                        if (LOG_ERRORS) log.error("Error for add book request", e);
                        errorCounts.incrementAndGet();
                        return e.getMessage();
                    });
        }).toList();
    }


    public List<CompletableFuture<String>> sleep() {
        log.info("Start long tasks in {} threads", IS_VIRTUAL ? "virtual" : "OS native");
        startMeasure();
        String port = getPort();
        return IntStream.range(0, SLEEPS_COUNT).mapToObj(it -> {
            Random random = new Random(System.currentTimeMillis());
            long time = random.nextLong(SLEEPS_MIN_TIME_MS, SLEEPS_MAX_TIME_MS);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format("http://localhost:%s/sleep/%d", port, time)))
                    .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                    .GET().build();
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        if (LOG_RESPONSES) log.info("Response body for sleep request: {}", response.body());
                        successCounts.incrementAndGet();
                        return response.body();
                    })
                    .exceptionally(e -> {
                        if (LOG_ERRORS) log.error("Error for sleep request", e);
                        errorCounts.incrementAndGet();
                        return e.getMessage();
                    });
        }).toList();
    }

    private static String getPort() {
        return IS_VIRTUAL ? "8083" : "8081";
    }

    private void startMeasure() {
        errorCounts.set(0);
        successCounts.set(0);
        startTime = System.nanoTime() / 1000_000_000.0;
    }

}
