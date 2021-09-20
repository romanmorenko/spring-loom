package ru.neoflex.springloom.controller;


public class BookNotFoundException extends Exception {

    public BookNotFoundException(String id) {
        super(String.format("Book with id %s not found", id));
    }
}
