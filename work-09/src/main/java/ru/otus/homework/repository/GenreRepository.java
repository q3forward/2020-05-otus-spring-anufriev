package ru.otus.homework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.domain.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    Genre getByName(String name);
}
