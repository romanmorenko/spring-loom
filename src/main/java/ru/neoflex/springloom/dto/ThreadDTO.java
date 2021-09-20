package ru.neoflex.springloom.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ThreadDTO {

    private final String info = Thread.currentThread().toString();
    private final Boolean isVirtual = Thread.currentThread().isVirtual();

}
