package ru.otus.homework.service;

import ru.otus.homework.domain.Genre;

public interface GenreService {

    Genre add(String name);
    Genre findByName(String name);
    Iterable<Genre> findAll();
}
