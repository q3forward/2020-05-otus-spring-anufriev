package ru.otus.homework.service;

import ru.otus.homework.domain.Book;
import ru.otus.homework.utils.exception.BookNotFoundException;

import java.util.List;

public interface BookService {
    Book add(String bookTitle, String authorName, String genreName);
    void update(long bookId, String bookTitle, String authorName, String genreName) throws BookNotFoundException;
    void delete(long bookId) throws BookNotFoundException;
    List<Book> getAll();
    Book getById(long bookId);
}
