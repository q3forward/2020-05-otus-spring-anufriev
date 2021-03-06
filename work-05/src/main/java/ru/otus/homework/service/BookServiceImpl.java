package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.utils.exception.BookNotFoundException;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImpl(BookDao bookDao, AuthorService authorService, GenreService genreService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public Book add(String bookTitle, String authorName, String genreName) {
        Book book = new Book();
        book.setTitle(bookTitle);
        setAuthorForBook(book, authorName);
        setGenreForBook(book, genreName);
        book.setId(bookDao.insert(book));
        return book;
    }

    @Override
    public void update(long bookId, String bookTitle, String authorName, String genreName) throws BookNotFoundException {
        Book book = bookDao.getById(bookId);
        if (book!=null) {
            if (!bookTitle.equals("")) {
                book.setTitle(bookTitle);
            }
            setAuthorForBook(book, authorName);
            setGenreForBook(book, genreName);
            bookDao.update(book);
        } else {
            throw new BookNotFoundException("Книга по указанному Id не найдена");
        }
    }

    @Override
    public void delete(long bookId) throws BookNotFoundException {
        if (bookDao.existsById(bookId)) {
            bookDao.deleteById(bookId);
        } else {
            throw new BookNotFoundException("Книга по указанному Id не найдена");
        }
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Book getById(long bookId) {
        return bookDao.getById(bookId);
    }

    private void setAuthorForBook(Book book, String authorName) {
        if (!authorName.equals("")) {
            List<Author> authors = authorService.findByName(authorName);

            Author author = null;
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
