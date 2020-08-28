package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.utils.exception.BookNotFoundException;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepo;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    public BookServiceImpl(BookRepository bookRepo, AuthorService authorService, GenreService genreService, CommentService commentService) {
        this.bookRepo = bookRepo;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
    }

    @Transactional
    @Override
    public Book add(String bookTitle, String authorName, String genreName) {
        Book book = new Book();
        book.setTitle(bookTitle);
        setAuthorForBook(book, authorName);
        setGenreForBook(book, genreName);
        return bookRepo.save(book);
    }

    @Transactional
    @Override
    public void update(long bookId, String bookTitle, String authorName, String genreName) throws BookNotFoundException {
        Optional<Book> optBook = bookRepo.findById(bookId);
        if (optBook.isPresent()) {
            Book book = optBook.get();
            if (!bookTitle.equals("")) {
                book.setTitle(bookTitle);
            }
            setAuthorForBook(book, authorName);
            setGenreForBook(book, genreName);
            bookRepo.save(book);
        } else {
            throw new BookNotFoundException("Книга по указанному Id не найдена");
        }
    }

    @Transactional
    @Override
    public void delete(long bookId) throws BookNotFoundException {
        if (bookRepo.existsById(bookId)) {
            commentService.deleteAllByBookId(bookId);
            bookRepo.deleteById(bookId);
        } else {
            throw new BookNotFoundException("Книга по указанному Id не найдена");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        return bookRepo.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Book getById(long bookId) {
        return bookRepo.findById(bookId).orElse(null);
    }

    private void setAuthorForBook(Book book, String authorName) {
        if (!authorName.equals("")) {
            List<Author> authors = authorService.findByName(authorName);

            Author author;
            if (authors.isEmpty()) {
                author = authorService.add(authorName);
            } else {
                author = authors.get(0);
            }
            book.setAuthor(author);
        }
    }

    private void setGenreForBook(Book book, String genreName) {
        if (!genreName.equals("")) {
            Genre genre = genreService.findByName(genreName);
            if (genre == null) {
                genre = genreService.add(genreName);
            }
            book.setGenre(genre);
        }
    }
}
