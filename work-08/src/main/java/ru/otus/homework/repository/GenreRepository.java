package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
    Genre getByName(String name);
}
