package ru.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService{

    private final GenreRepository genreRepo;

    public GenreServiceImpl(GenreRepository genreRepo) {
        this.genreRepo = genreRepo;
    }

    @HystrixCommand(fallbackMethod = "addGenre_Fallback")
    @Transactional
    @Override
    public Genre add(String genreName) {
        Genre genre = new Genre(genreName);
        return genreRepo.save(genre);
    }

    @HystrixCommand(fallbackMethod = "findByNameGenre_Fallback")
    @Transactional(readOnly = true)
    @Override
    public Genre findByName(String name) {
        return genreRepo.getByName(name);
    }

    @HystrixCommand(fallbackMethod = "findAllGenre_Fallback")
    @Transactional(readOnly = true)
    @Override
    public List<Genre> findAll() {
        return genreRepo.findAll();
    }

    private Genre addGenre_Fallback() {
        System.out.println("Something going wrong on add genre");
        return new Genre("N/A");
    }

    private Genre findByNameGenre_Fallback(String name) {
        return new Genre("N/A");
    }

    private List<Genre> findAllGenre_Fallback() {
        return new ArrayList<Genre>();
    }
}
