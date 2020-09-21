package ru.otus.homework.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.GenreRepository;

import java.util.Optional;

@RestController
public class BookController {
    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;
    private final GenreRepository genreRepo;

    @Autowired
    public BookController(BookRepository bookRepo, AuthorRepository authorRepo, GenreRepository genreRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.genreRepo = genreRepo;
    }

    @GetMapping(value = "/api/book")
    public Flux<Book> getBooks() {
        return bookRepo.findAll();
    }

    @GetMapping(value = "/api/book/{id}")
    public Mono<Book> getBookById(@PathVariable String id) {
        return bookRepo.findById(id);
    }

    @DeleteMapping(value="/api/book/{id}")
    public Mono<ResponseEntity<Void>> deleteBook(@PathVariable String id) {
        return bookRepo.findById(id)
                .flatMap(book -> bookRepo.delete(book)
                                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/book")
    public Mono<ResponseEntity<Void>> addBook(@RequestBody BookDto bookDto) {
        Book book = new Book();
        return authorRepo.findAllByName(getAuthorName(bookDto))
                    .flatMap(author -> {
                        book.setAuthor(author);
                        return genreRepo.findByName(getGenreName(bookDto));
                    }).flatMap(genre -> {
                        book.setGenre(genre);
                        book.setTitle(bookDto.getTitle());
                        return bookRepo.save(book).then(Mono.just(ResponseEntity.ok().<Void>build()));
                    }).then(Mono.just(ResponseEntity.ok().<Void>build()));
    }

    @PutMapping("/api/book/{id}")
    public Mono<ResponseEntity<Void>> editBook(@PathVariable String id, @RequestBody BookDto bookDto) {
        Book book = new Book();
        return bookRepo.findById(id).flatMap(foundBook -> {
            book.setId(foundBook.getId());
            book.setTitle(bookDto.getTitle());
            return authorRepo.findAllByName(getAuthorName(bookDto)).flatMap(author -> {
                book.setAuthor(author);
                return genreRepo.findByName(getGenreName(bookDto));
            }).flatMap(genre -> {
                book.setGenre(genre);
                return bookRepo.save(book).then(Mono.just(ResponseEntity.ok().<Void>build()));
            }).then(Mono.just(ResponseEntity.ok().<Void>build()));
        }).defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    private String getAuthorName(BookDto bookDto) {
        return Optional.ofNullable(bookDto.getAuthor())
                .map(Author::getName)
                .orElse(bookDto.getNewAuthorName());
    }

    private String getGenreName(BookDto bookDto) {
        return Optional.ofNullable(bookDto.getGenre())
                .map(Genre::getName)
                .orElse(bookDto.getNewGenreName());
    }
}
