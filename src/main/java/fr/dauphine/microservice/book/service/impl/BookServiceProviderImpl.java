package fr.dauphine.microservice.book.service.impl;


import fr.dauphine.microservice.book.model.Book;
import fr.dauphine.microservice.book.repository.BookRepository;
import fr.dauphine.microservice.book.service.BookServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceProviderImpl implements BookServiceProvider {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public List<Book> findByAuthor(String authorName) {
        return bookRepository.findByAuthor(authorName);
    }

    @Override
    public List<Book> findByEditor(String editor) {
        return bookRepository.findByEditor(editor);
    }

    @Override
    public List<Book> findByEdition(int year) {
        return bookRepository.findByEdition(year);
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public Book update(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }
}
