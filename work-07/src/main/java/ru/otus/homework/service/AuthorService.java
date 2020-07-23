package ru.otus.homework.service;

import ru.otus.homework.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author add(String authorName);
    Optional<Author> findById(long authorId);
    List<Author> findByName(String authorName);
    Iterable<Author> findAll();
}
