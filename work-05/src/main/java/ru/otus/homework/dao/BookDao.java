package ru.otus.homework.dao;

import ru.otus.homework.domain.Book;

import java.util.List;

public interface BookDao {
    int count();
    long insert(Book book);
    void update(Book book);
    Book getById(long id);
    List<Book> getAll();
    void deleteById(long id);
    boolean existsById(long id);
}
