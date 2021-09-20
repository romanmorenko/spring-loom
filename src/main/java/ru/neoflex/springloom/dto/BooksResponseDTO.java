package ru.neoflex.springloom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class BooksResponseDTO {

    private List<BookDTO> items;
    private final ThreadDTO tread = new ThreadDTO();

    public BooksResponseDTO(List<BookDTO> items) {

        this.items = items;
    }
}
