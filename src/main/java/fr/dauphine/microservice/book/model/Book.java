package fr.dauphine.microservice.book.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Book {
    @Id
    private String isbn;

    public String getIsbn() {
        return isbn;
    }

    public Book setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    private String author;
    private String title;
    private String editor;
    private int edition;

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getEditor() {
        return editor;
    }

    public Book setEditor(String editor) {
        this.editor = editor;
        return this;
    }

    public int getEdition() {
        return edition;
    }

    public Book setEdition(int edition) {
        this.edition = edition;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", editor='" + editor + '\'' +
                ", edition=" + edition +
                '}';
    }

    public Book update(Book book) {
        if(Objects.nonNull(book.author)) this.author = book.author;
        if(Objects.nonNull(book.editor)) this.editor = book.editor;
        if(Objects.nonNull(book.title)) this.title = book.title;
        if(book.edition != 0) this.edition = book.edition;
        return this;
    }
}
