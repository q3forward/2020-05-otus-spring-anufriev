package ru.otus.homework.service;

import ru.otus.homework.domain.Author;

import java.util.List;

public interface AuthorService {
    Author add(String authorName);
    Author findById(long authorId);
    List<Author> findByName(String authorName);
    List<Author> findAll();
}
