package fr.dauphine.microservice.book.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BookTest {


    Book book;

    @Before
    public void setUp() {
        book = new Book();
    }

    @Test
    public void testIsbnSetterAndGetter() {
        book.setIsbn("12345");
        assertEquals("12345", book.getIsbn());
    }

    @Test
    public void testEditionSetterAndGetter() {
        book.setEdition(2020);
        assertEquals(2020, book.getEdition());
    }

    @Test
    public void testEditorSetterAndGetter() {
        book.setEditor("Lebimo18");
        assertEquals("Lebimo18", book.getEditor());
    }

    @Test
    public void testTitleSetterAndGetter() {
        book.setTitle("Parerga");
        assertEquals("Parerga", book.getTitle());
    }

    @Test
    public void testEquals() {
        Book book2 = new Book();
        book2.setIsbn("12345");
        book.setIsbn("12345");
        assertEquals(book, book2);
    }

    @Test
    public void testNotEquals() {
        Book book2 = new Book();
        book2.setIsbn("12345");
        book.setIsbn("12346");
        assertNotEquals(book, book2);
    }

    @Test
    public void testSameHashcode() {
        Book book2 = new Book();
        book2.setIsbn("12345");
        book.setIsbn("12345");
        assertEquals(book.hashCode(), book2.hashCode());
    }

    @Test
    public void testNotSameHashcode() {
        Book book2 = new Book();
        book2.setIsbn("12345");
        book.setIsbn("12346");
        assertNotEquals(book.hashCode(), book2.hashCode());
    }

    @Test
    public void testNotEmptyToString() {
        assertNotEquals("", book.toString());
    }
}
