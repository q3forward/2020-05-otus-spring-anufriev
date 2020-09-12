package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.jpa.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre getByName(String name);
}
