package ru.otus.homework.rest;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;
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
    public Mono<Void> deleteBook(@PathVariable String id) {
        return bookRepo.findById(id)
                .flatMap(book -> bookRepo.delete(book));
    }

    @PostMapping("/api/book")
    public Mono<Book> addBook(@RequestBody BookDto bookDto) {
        Mono<Author> ma = getOrCreateAuthor(getAuthorName(bookDto));
        Mono<Genre> mg = getOrCreateGenre(getGenreName(bookDto));

        return Mono.zip(ma, mg, (author, genre) -> new Book(bookDto.getTitle(), author, genre))
                .flatMap(book -> bookRepo.save(book));
    }

    @PutMapping("/api/book/{id}")
    public Mono<Book> editBook(@PathVariable String id, @RequestBody BookDto bookDto) {
        Mono<Author> ma = getOrCreateAuthor(getAuthorName(bookDto));
        Mono<Genre> mg = getOrCreateGenre(getGenreName(bookDto));

        return bookRepo.findById(id).flatMap(foundBook ->
            Mono.zip(ma, mg, (author, genre) ->
                new Book(foundBook.getId(), bookDto.getTitle(), author, genre)
            ).flatMap(book -> bookRepo.save(book)));
    }

    private Mono<Author> getOrCreateAuthor(String authorName) {
        Mono<List<Author>> mla = authorRepo.findAllByName(authorName).collectList();
        return mla.flatMap(authorList -> {
            if (authorList.isEmpty()) {
                return authorRepo.save(new Author(authorName));
            } else {
                return Mono.just(authorList.get(0));
            }
        });
    }

    private Mono<Genre> getOrCreateGenre(String genreName) {
        return genreRepo.findByName(genreName)
                .switchIfEmpty(genreRepo.save(new Genre(genreName)));
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
