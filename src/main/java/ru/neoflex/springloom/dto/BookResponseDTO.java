package ru.neoflex.springloom.dto;

import liquibase.pro.packaged.A;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BookResponseDTO {

    private BookDTO item;
    private final String currentThread = Thread.currentThread().toString();
    private final Boolean isVirtualThread = Thread.currentThread().isVirtual();

    public BookResponseDTO(BookDTO item) {

        this.item = item;
    }
}
