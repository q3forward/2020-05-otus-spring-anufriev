package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Genre;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService{

    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public Genre add(String genreName) {
        Genre genre = new Genre(genreName);
        genre.setId(genreDao.insert(genre));
        return genre;
    }

    @Override
    public Genre findByName(String name) {
        return genreDao.getByName(name);
    }

    @Override
    public List<Genre> findAll() {
        return genreDao.getAll();
    }
}
