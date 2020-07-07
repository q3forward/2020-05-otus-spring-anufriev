package ru.otus.homework.dao;

import ru.otus.homework.domain.Author;

import java.util.List;

public interface AuthorDao {
    int count();

    long insert(Author author);

    void update(Author author);

    Author getById(long id);

    List<Author> getByName(String name);

    List<Author> getAll();

    void deleteById(long id);
}
