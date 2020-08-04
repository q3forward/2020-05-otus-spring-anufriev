package ru.otus.homework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.domain.Genre;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, String> {
    Genre getByName(String name);
}
