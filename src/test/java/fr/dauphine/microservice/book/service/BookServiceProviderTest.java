package fr.dauphine.microservice.book.service;

import fr.dauphine.microservice.book.dto.BookDto;
import fr.dauphine.microservice.book.model.Book;
import fr.dauphine.microservice.book.repository.BookRepository;
import fr.dauphine.microservice.book.service.impl.BookServiceProviderImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceProviderTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookServiceProviderImpl bookServiceProvider;


    @Test
    public void testBookCreation() {
        Book book = new Book().setIsbn("12345");
        when(bookRepository.save(book)).thenReturn(book);
        assertEquals(book, bookServiceProvider.create(book));
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = List.of(new Book().setIsbn("12345"),
                                   new Book().setIsbn("34561"));
        when(bookRepository.findAll()).thenReturn(books);
        assertEquals(books, bookServiceProvider.getAll());
    }

    @Test
    public void testFindByIsbn() {
        Book book = new Book().setIsbn("12345");
        when(bookRepository.findById("12345")).thenReturn(Optional.of(book));
        assertEquals(book, bookServiceProvider.findByIsbn("12345"));
    }

    @Test(expected = NoSuchElementException.class)
    public void testByUnknownIsbn() {
        when(bookRepository.findById("12345")).thenThrow(NoSuchElementException.class);
        bookServiceProvider.findByIsbn("12345");
    }

    @Test
    public void testFindByAuthor() {
        List<Book> books = List.of(new Book().setIsbn("12345"),
                new Book().setIsbn("34561"));
        when(bookRepository.findByAuthor("Hamiho")).thenReturn(books);
        assertEquals(books, bookServiceProvider.findByAuthor("Hamiho"));
    }

    @Test
    public void testFindByEditor() {
        List<Book> books = List.of(new Book().setIsbn("12345"),
                new Book().setIsbn("34561"));
        when(bookRepository.findByEditor("Hamiho")).thenReturn(books);
        assertEquals(books, bookServiceProvider.findByEditor("Hamiho"));
    }

    @Test
    public void testFindByTitle() {
        List<Book> books = List.of(new Book().setIsbn("12345"),
                new Book().setIsbn("34561"));
        when(bookRepository.findByTitle("Ayrad")).thenReturn(books);
        assertEquals(books, bookServiceProvider.findByTitle("Ayrad"));
    }

    @Test
    public void testFindByEdition() {
        List<Book> books = List.of(new Book().setIsbn("12345"),
                new Book().setIsbn("34561"));
        when(bookRepository.findByEdition(2020)).thenReturn(books);
        assertEquals(books, bookServiceProvider.findByEdition(2020));
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book().setIsbn("12345").setTitle("Ayrad");
        when(bookRepository.findById("12345")).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(new Book().setIsbn("12345").setTitle("Ayrad, 2nd edition"));
        book.setTitle("Ayrad, 2nd edition");
        assertEquals(book, bookServiceProvider.update(book));
    }

    @Test
    public void testDeleteBook() {
        Book book = new Book().setIsbn("12345");
        doNothing().when(bookRepository).delete(any());
        bookServiceProvider.delete(book);
        verify(bookRepository, times(1)).delete(book);
    }
}
