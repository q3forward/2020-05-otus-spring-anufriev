package ru.otus.homework.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;
import ru.otus.homework.utils.exception.BookNotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping(value = "/api/books")
    public List<Book> getBooks() {
        List<Book> books = bookService.getAll();
        return books;
    }

    @GetMapping(value = "/api/book/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @DeleteMapping(value="/api/book/{id}")
    public ResponseEntity deleteBook(@PathVariable Long id) throws BookNotFoundException {
        bookService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/api/book")
    public ResponseEntity addBook(@RequestBody BookDto bookDto) {
        String authorName = getAuthorName(bookDto);
        String genreName = getGenreName(bookDto);

        bookService.add(bookDto.getTitle(), authorName, genreName);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/api/book/{id}")
    public ResponseEntity editBook(@PathVariable Long id, @RequestBody BookDto bookDto) throws BookNotFoundException {
        String authorName = getAuthorName(bookDto);
        String genreName = getGenreName(bookDto);

        bookService.update(id, bookDto.getTitle(), authorName, genreName);
        return new ResponseEntity(HttpStatus.OK);
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
