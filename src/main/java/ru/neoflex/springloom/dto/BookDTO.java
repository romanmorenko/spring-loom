package ru.neoflex.springloom.dto;


import lombok.*;
import ru.neoflex.springloom.entity.Book;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO {

    private String id;
    private String name;

    public BookDTO(String name) {
        this.name = name;
    }

    public static BookDTO mapToBookDTO(Book book) {

        return new BookDTO(book.getId().toString(), book.getName());
    }

    public static Book mapToBook(BookDTO bookDTO) {

        return new Book(bookDTO.getId() == null ? null
                : Long.valueOf(bookDTO.getId()), bookDTO.getName());
    }

}
