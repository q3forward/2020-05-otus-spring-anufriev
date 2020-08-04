package ru.otus.homework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.domain.Author;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, String> {
    List<Author> getByName(String name);
}
