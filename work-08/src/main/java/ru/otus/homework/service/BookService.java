package ru.otus.homework.service;

import ru.otus.homework.domain.Book;
import ru.otus.homework.utils.exception.BookNotFoundException;

public interface BookService {
    Book add(String bookTitle, String authorName, String genreName);
    void update(String bookId, String bookTitle, String authorName, String genreName) throws BookNotFoundException;
    void delete(String bookId) throws BookNotFoundException;
    Iterable<Book> getAll();
    Book getById(String bookId);
}
