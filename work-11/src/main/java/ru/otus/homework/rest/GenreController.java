package ru.otus.homework.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.GenreRepository;

@RestController
public class GenreController {
    private final GenreRepository genreRepo;

    @Autowired
    public GenreController(GenreRepository genreRepo) {
        this.genreRepo = genreRepo;
    }

    @GetMapping(value = "/api/genres")
    public Flux<Genre> getGenres() {
        return genreRepo.findAll();
    }

}
