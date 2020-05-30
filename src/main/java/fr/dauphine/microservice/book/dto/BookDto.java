package fr.dauphine.microservice.book.dto;

import fr.dauphine.microservice.book.model.Book;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Id;
import java.util.Objects;

public class BookDto extends RepresentationModel<BookDto> {

    private String isbn;

    public String getIsbn() {
        return isbn;
    }

    public BookDto setIsbn(String isbn) {
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

    public BookDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getEditor() {
        return editor;
    }

    public BookDto setEditor(String editor) {
        this.editor = editor;
        return this;
    }

    public int getEdition() {
        return edition;
    }

    public BookDto setEdition(int edition) {
        this.edition = edition;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto book = (BookDto) o;
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

    public BookDto fill(Book book) {
        this.author = book.getAuthor();
        this.edition = book.getEdition();
        this.editor = book.getEditor();
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        return this;
    }
}
