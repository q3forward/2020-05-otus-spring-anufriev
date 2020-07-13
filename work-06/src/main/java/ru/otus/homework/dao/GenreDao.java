package ru.otus.homework.dao;

import ru.otus.homework.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    long count();

    Genre save(Genre genre);

    Optional<Genre> getById(long id);

    Genre getByName(String name);

    List<Genre> getAll();

    void deleteById(long id);
}
