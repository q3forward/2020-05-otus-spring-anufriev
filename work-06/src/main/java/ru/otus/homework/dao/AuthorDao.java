package ru.otus.homework.dao;

import ru.otus.homework.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    long count();

    Author save(Author author);

    Optional<Author> getById(long id);

    List<Author> getByName(String name);

    List<Author> getAll();

    void deleteById(long id);
}
