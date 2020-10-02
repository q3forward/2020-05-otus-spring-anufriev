package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.homework.domain.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
    Flux<Author> findAllByName(String name);
}
