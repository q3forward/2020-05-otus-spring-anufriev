package ru.otus.homework.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.homework.domain.Author;
import ru.otus.homework.repository.AuthorRepository;

@RestController
public class AuthorController {
    private final AuthorRepository authorRepo;

    @Autowired
    public AuthorController(AuthorRepository authorRepo) {
        this.authorRepo = authorRepo;
    }

    @GetMapping(value = "/api/authors")
    public Flux<Author> getAuthors() {
        return authorRepo.findAll();
    }

}
