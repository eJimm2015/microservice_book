package fr.dauphine.microservice.book.api;

import fr.dauphine.microservice.book.model.Book;
import fr.dauphine.microservice.book.service.BookServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/books")
public class BookApi {
    
    @Autowired
    private BookServiceProvider bookServiceProvider;

    @PostMapping
    public ResponseEntity<EntityModel<Book>> create(@RequestBody Book book) {
        Book created = bookServiceProvider.create(book);
        Link link = getLink(created.getIsbn());
        return new ResponseEntity<>(EntityModel.of(created, link), CREATED);
    }

    @GetMapping("{isbn}")
    public ResponseEntity<EntityModel<Book>> findByIsbn(@PathVariable("isbn") String isbn) {

        Optional<Book> created = bookServiceProvider.findByIsbn(isbn);
        Book book = created.orElse(new Book());
        Link link = getLink(isbn);
        return new ResponseEntity<>(EntityModel.of(book, link), CREATED);
    }


    @GetMapping
    public ResponseEntity<CollectionModel<Book>> findBy(@RequestParam(value = "author", required = false) String author,
                                                        @RequestParam(value = "editor", required = false) String editor,
                                                        @RequestParam(value = "title", required = false) String title,
                                                        @RequestParam(value = "edition", required = false) Integer edition) {
        List<Book> books;
        if(author != null) books = bookServiceProvider.findByAuthor(author);
        else if(editor != null) books = bookServiceProvider.findByEditor(editor);
        else if(title != null) books = bookServiceProvider.findByTitle(title);
        else if(edition != null) books = bookServiceProvider.findByEdition(edition);
        else books = bookServiceProvider.getAll();
        List<Book> mapped = books.stream()
                .map(e -> e.add(getLink(e.getIsbn())))
                .collect(Collectors.toList());
        Link link = linkTo(methodOn(BookApi.class)
                .findBy(author, editor, title, edition)).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(mapped, link));
    }
    @PutMapping()
    public ResponseEntity<EntityModel<Book>> update(@RequestBody Book book) {
        Book updated = bookServiceProvider.update(book);
        return ResponseEntity.ok().body(EntityModel.of(updated, getLink(updated.getIsbn())));
    }
    @DeleteMapping("{isbn}")
    public ResponseEntity<Void> delete(@PathVariable String isbn) {
        bookServiceProvider.delete(new Book().setIsbn(isbn));
        return ResponseEntity.ok().build();
    }


    private Link getLink(String isbn) {
        return linkTo(methodOn(BookApi.class)
                .findByIsbn(isbn)).withSelfRel();
    }

}
