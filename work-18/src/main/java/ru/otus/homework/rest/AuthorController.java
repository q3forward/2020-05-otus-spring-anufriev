package ru.otus.homework.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.domain.Author;
import ru.otus.homework.service.AuthorService;

@RestController
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "/api/authors")
    public Iterable<Author> getAuthors() {
        return authorService.findAll();
    }

}
