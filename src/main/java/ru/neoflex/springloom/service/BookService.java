package ru.neoflex.springloom.service;

import org.springframework.stereotype.Service;
import ru.neoflex.springloom.entity.Book;
import ru.neoflex.springloom.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> all() {
        return bookRepository.findAll();
    }

    public Optional<Book> get(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        }
    }

    public void sleep(long time) throws InterruptedException {
        Thread.sleep(1000);
    }

    public void clear() {
        bookRepository.deleteAll();
    }
}
