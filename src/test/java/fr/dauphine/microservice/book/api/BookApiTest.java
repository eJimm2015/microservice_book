package fr.dauphine.microservice.book.api;

import fr.dauphine.microservice.book.dto.BookDto;
import fr.dauphine.microservice.book.model.Book;
import fr.dauphine.microservice.book.repository.BookRepository;
import fr.dauphine.microservice.book.service.BookServiceProvider;
import fr.dauphine.microservice.book.service.impl.BookServiceProviderImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookApiTest {

    @Mock
    BookServiceProvider bookServiceProvider;

    @InjectMocks
    BookApi bookApi;

    @Test
    public void testCreation(){
        Book book = new Book();
        Mockito.when(bookServiceProvider.create(book)).thenReturn(book.setIsbn("12345"));
        ResponseEntity<EntityModel<BookDto>> entityModelResponseEntity = bookApi.create(book);
        EntityModel<BookDto> body = entityModelResponseEntity.getBody();
        Assert.assertNotNull(body);
        BookDto content = body.getContent();
        Assert.assertEquals(new BookDto().fill(book.setIsbn("12345")),content);
    }

    @Test
    public void testFindByIsbn(){
        Book book = new Book().setIsbn("12345");
        Mockito.when(bookServiceProvider.findByIsbn("12345")).thenReturn(book);
        ResponseEntity<EntityModel<BookDto>> entityModelResponseEntity= bookApi.findByIsbn("12345");
        EntityModel<BookDto> body = entityModelResponseEntity.getBody();
        Assert.assertNotNull(body);
        BookDto content = body.getContent();
        Assert.assertEquals(new BookDto().fill(book),content);
    }

    @Test(expected = ResponseStatusException.class)
    public void testFindByUnknownIsbn() {
        Mockito.when(bookServiceProvider.findByIsbn("12345")).thenThrow(NoSuchElementException.class);
        bookApi.findByIsbn("12345");
    }

    @Test(expected = ResponseStatusException.class)
    public void testUnknownBookUpdate(){
        Book book= new Book();
        Mockito.when(bookServiceProvider.update(book)).thenThrow(NoSuchElementException.class);
        bookApi.update(book);
    }

    @Test
    public void testUpdate(){
        Book book = new Book().setIsbn("12345").setAuthor("Matoub");
        Mockito.when(bookServiceProvider.update(book.setAuthor("Oulahlou"))).thenReturn(book.setAuthor("Oulahlou"));
        book.setAuthor("Oulahlou");
        ResponseEntity<EntityModel<BookDto>> entityModelResponseEntity= bookApi.update(book);
        EntityModel<BookDto> body = entityModelResponseEntity.getBody();
        Assert.assertNotNull(body);
        BookDto content = body.getContent();
        Assert.assertEquals(new BookDto().fill(book),content);

    }
    @Test
    public void testFindBy(){
        Book book = new Book().setIsbn("12345");
        List<Book> books = new ArrayList<>();
        books.add(new Book().setIsbn("12345"));
        books.add(new Book().setIsbn("7891"));
        when(bookServiceProvider.findByAuthor("Saadi")).thenReturn(books);
        ResponseEntity<CollectionModel<BookDto>> byDate = bookApi.findBy("Saadi",null,null, null);
        CollectionModel<BookDto> body = byDate.getBody();
        assertNotNull(body);
        assertEquals(books.stream().map(e->new BookDto().fill(e)).collect(Collectors.toList()), new ArrayList<>(body.getContent()));
    }

    @Test
    public void testDelete(){
        Book book = new Book().setIsbn("12345");
        doNothing().when(bookServiceProvider).delete(book);
        bookApi.delete(book.getIsbn());
        verify(bookServiceProvider, times(1)).delete(book);
    }

}
