package ru.neoflex.springloom.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice(assignableTypes = BookController.class)
public class BookControllerAdvice {

    @ExceptionHandler
    @ResponseBody
    ResponseEntity<String> handle(BookNotFoundException ex)  {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( ex.getMessage());
    }
}

