package ru.otus.homework.service;

import ru.otus.homework.domain.Genre;

import java.util.List;

public interface GenreService {

    Genre add(String name);
    Genre findByName(String name);
    List<Genre> findAll();
}
