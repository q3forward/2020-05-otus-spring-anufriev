package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.GenreRepository;

@Service
public class GenreServiceImpl implements GenreService{

    private final GenreRepository genreRepo;

    public GenreServiceImpl(GenreRepository genreRepo) {
        this.genreRepo = genreRepo;
    }

    @Transactional
    @Override
    public Genre add(String genreName) {
        Genre genre = new Genre(genreName);
        return genreRepo.save(genre);
    }

    @Transactional(readOnly = true)
    @Override
    public Genre findByName(String name) {
        return genreRepo.getByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<Genre> findAll() {
        return genreRepo.findAll();
    }
}
