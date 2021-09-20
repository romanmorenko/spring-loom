package ru.neoflex.springloom.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title = "Controller for books", version = "1"))
public class BookController {

    private final BookService bookService;

    @GetMapping("/book/{id}")
    @ResponseBody
    @Operation(description = "Get book by id", method = "GET")
    @ApiResponse(responseCode = "200", description = "Book")
    @ApiResponse(responseCode = "404", description = "Not found")
    public ResponseEntity<BookResponseDTO> get(@Parameter(name = "id", required = true, example = "2")
                                               @PathVariable(name = "id") String id) throws BookNotFoundException {
        final Optional<Book> bookOptional = bookService.get(Long.valueOf(id));
        return ResponseEntity.ok(
                new BookResponseDTO(
                        BookDTO.mapToBookDTO(
                                bookOptional.orElseThrow(() -> new BookNotFoundException(id)))
                )
        );
    }

    @GetMapping("/book")
    @ResponseBody
    @Operation(description = "Get all books", method = "GET")
    @ApiResponse(responseCode = "200", description = "Books")
    public ResponseEntity<BooksResponseDTO> get() {

        return ResponseEntity.ok(
                new BooksResponseDTO(
                        bookService.all().stream()
                                .map(BookDTO::mapToBookDTO).toList()
                )
        );
    }


    @PostMapping("/book")
    @ResponseBody
    @Operation(description = "Add book", method = "POST")
    @ApiResponse(responseCode = "201", description = "Books")
    public ResponseEntity<BookResponseDTO> add(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "book")
            @RequestBody BookDTO bookDTO) throws UnknownHostException {

        Book book = bookService.saveBook(BookDTO.mapToBook(bookDTO));
        return ResponseEntity
                .created(URI.create("http://" + InetAddress.getLocalHost().getHostName()
                        + "/book/" + book.getId()))
                .body(new BookResponseDTO(BookDTO.mapToBookDTO(book)));
    }


    @DeleteMapping("/book/{id}")
    @ResponseBody
    @Operation(description = "Delete book", method = "DELETE")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<String> delete(@PathVariable(name = "id") String id) {

        bookService.delete(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/book")
    @ResponseBody
    @Operation(description = "Delete all books", method = "DELETE")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<String> clear() {

        bookService.clear();
        return ResponseEntity.noContent().build();
    }


}
