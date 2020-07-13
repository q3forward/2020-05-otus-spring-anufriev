package ru.otus.homework.dao;

import ru.otus.homework.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    long count();
    Book save(Book book);
    Optional<Book> getById(long id);
    List<Book> getAll();
    void deleteById(long id);
    boolean existsById(long id);
}
