package fr.dauphine.microservice.book.repository;

import fr.dauphine.microservice.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByAuthor(final String authorName);
    List<Book> findByTitle(final String title);
    List<Book> findByEditor(final String editor);
    List<Book> findByEdition(final int year);
}
