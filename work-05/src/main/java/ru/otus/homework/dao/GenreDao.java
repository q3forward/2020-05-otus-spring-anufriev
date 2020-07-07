package ru.otus.homework.dao;

import ru.otus.homework.domain.Genre;

import java.util.List;

public interface GenreDao {
    int count();

    long insert(Genre genre);

    Genre getById(long id);

    Genre getByName(String name);

    List<Genre> getAll();

    void deleteById(long id);
}
