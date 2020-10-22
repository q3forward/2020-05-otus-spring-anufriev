package ru.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.utils.exception.BookNotFoundException;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepo;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;
    private final String NA = "N/A";

    public BookServiceImpl(BookRepository bookRepo, AuthorService authorService, GenreService genreService, CommentService commentService) {
        this.bookRepo = bookRepo;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
    }

    @HystrixCommand(fallbackMethod = "addBook_Fallback")
    @Transactional
    @Override
    public Book add(String bookTitle, String authorName, String genreName) {
        Book book = new Book();
        book.setTitle(bookTitle);
        setAuthorForBook(book, authorName);
        setGenreForBook(book, genreName);
        return bookRepo.save(book);
    }

    @HystrixCommand(fallbackMethod = "updateBook_Fallback")
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

    @HystrixCommand(fallbackMethod = "deleteBook_Fallback")
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

    @HystrixCommand(fallbackMethod = "getAllBook_Fallback")
    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        return bookRepo.findAll();
    }

    @HystrixCommand(fallbackMethod = "getByIdBook_Fallback")
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

    private Book addBook_Fallback(String bookTitle, String authorName, String genreName, Throwable e) {
        if (e!=null) System.out.println(e.getMessage());
        throw new RuntimeException("Something going wrong on add book");
    }

    private void updateBook_Fallback(long bookId, String bookTitle, String authorName, String genreName, Throwable e) {
        if (e!=null) System.out.println(e.getMessage());
        throw new RuntimeException("Something going wrong on update book");
    }

    private void deleteBook_Fallback(long bookId, Throwable e) {
        if (e!=null) System.out.println(e.getMessage());
        throw new RuntimeException("Something going wrong on delete book");
    }

    private List<Book> getAllBook_Fallback() {
        return new ArrayList();
    }

    private Book getByIdBook_Fallback(long bookId) {
        Book book = new Book();
        book.setTitle(NA);
        book.setGenre(new Genre(NA));
        book.setAuthor(new Author(NA));
        return book;
    }

    private void addWaiting() {
        try {
            System.out.println("Start sleep...." + new Date());
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            System.out.println("Hystrix thread interupted...." + new Date());
        }
    }
}
