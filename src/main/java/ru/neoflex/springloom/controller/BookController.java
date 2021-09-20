package ru.neoflex.springloom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.springloom.dto.BookDTO;
import ru.neoflex.springloom.dto.BookResponseDTO;
import ru.neoflex.springloom.dto.BooksResponseDTO;
import ru.neoflex.springloom.entity.Book;
import ru.neoflex.springloom.service.BookService;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/book/{id}")
    @ResponseBody
    public ResponseEntity<BookResponseDTO> get(@PathVariable(name = "id") String id) {

        return ResponseEntity.ok(
                new BookResponseDTO(
                        BookDTO.mapToBookDTO(bookService.get(Long.valueOf(id)).get())
                )
        );
    }

    @GetMapping("/book")
    @ResponseBody
    public ResponseEntity<BooksResponseDTO> get() {

        return ResponseEntity.ok(
                new BooksResponseDTO(
                        bookService.all().stream()
                                .map(BookDTO::mapToBookDTO).collect(Collectors.toList())
                )
        );
    }

    @PostMapping("/book")
    @ResponseBody
    public ResponseEntity<BookResponseDTO> add(@RequestBody BookDTO bookDTO) throws UnknownHostException {

        Book book = bookService.saveBook(BookDTO.mapToBook(bookDTO));
        return ResponseEntity
                .created(URI.create("http://" + InetAddress.getLocalHost().getHostName()
                        + "/book/" + book.getId()))
                .body(new BookResponseDTO(BookDTO.mapToBookDTO(book)));
    }


    @DeleteMapping("/book/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable(name = "id") String id) {

        bookService.delete(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/book")
    @ResponseBody
    public ResponseEntity clear() {

        bookService.clear();
        return ResponseEntity.noContent().build();
    }


}
