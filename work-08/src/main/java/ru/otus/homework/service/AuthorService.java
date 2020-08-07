package ru.otus.homework.service;

import ru.otus.homework.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author add(String authorName);
    Optional<Author> findById(String authorId);
    List<Author> findByName(String authorName);
    List<Author> findAll();
}
