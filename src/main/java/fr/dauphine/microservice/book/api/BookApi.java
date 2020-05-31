package fr.dauphine.microservice.book.api;

import fr.dauphine.microservice.book.dto.BookDto;
import fr.dauphine.microservice.book.model.Book;
import fr.dauphine.microservice.book.service.BookServiceProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/books")
@Api(value = "Book API")
public class BookApi {
    
    @Autowired
    private BookServiceProvider bookServiceProvider;

    @PostMapping
    @ApiOperation("Create Book")
    public ResponseEntity<EntityModel<BookDto>> create(@RequestBody Book book) {
        Book created = bookServiceProvider.create(book);
        Link link = getLink(created.getIsbn());
        return new ResponseEntity<>(EntityModel.of(new BookDto().fill(created), link), CREATED);
    }

    @GetMapping("{isbn}")
    @ApiOperation("Find Book by ISBN")
    public ResponseEntity<EntityModel<BookDto>> findByIsbn(@PathVariable("isbn") String isbn) {
        try {
            Book created = bookServiceProvider.findByIsbn(isbn);
            Link link = getLink(isbn);
            return new ResponseEntity<>(EntityModel.of(new BookDto().fill(created), link), OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(NOT_FOUND,e.getMessage());
        }

    }


    @GetMapping
    @ApiOperation("Find by Attributes")
    public ResponseEntity<CollectionModel<BookDto>> findBy(@RequestParam(value = "author", required = false) String author,
                                                        @RequestParam(value = "editor", required = false) String editor,
                                                        @RequestParam(value = "title", required = false) String title,
                                                        @RequestParam(value = "edition", required = false) Integer edition) {
        List<Book> books;
        if(author != null) books = bookServiceProvider.findByAuthor(author);
        else if(editor != null) books = bookServiceProvider.findByEditor(editor);
        else if(title != null) books = bookServiceProvider.findByTitle(title);
        else if(edition != null) books = bookServiceProvider.findByEdition(edition);
        else books = bookServiceProvider.getAll();
        List<BookDto> mapped = books.stream()
                .map(e -> new BookDto().fill(e).add(getLink(e.getIsbn())))
                .collect(Collectors.toList());
        Link link = linkTo(methodOn(BookApi.class)
                .findBy(author, editor, title, edition)).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(mapped, link));
    }
    @PutMapping
    @ApiOperation("Update Book")
    public ResponseEntity<EntityModel<BookDto>> update(@RequestBody Book book) {
       try {
           Book updated = bookServiceProvider.update(book);
           return ResponseEntity.ok().body(EntityModel.of(new BookDto().fill(updated), getLink(updated.getIsbn())));
       }catch (NoSuchElementException e){
           throw new ResponseStatusException(NOT_FOUND,e.getMessage());
       }
    }
    @DeleteMapping("{isbn}")
    @ApiOperation("Delete Book")
    public ResponseEntity<Void> delete(@PathVariable String isbn) {
        bookServiceProvider.delete(new Book().setIsbn(isbn));
        return ResponseEntity.ok().build();
    }


    private Link getLink(String isbn) {
        return linkTo(methodOn(BookApi.class)
                .findByIsbn(isbn)).withSelfRel();
    }
}
