package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Genre;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService{

    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Transactional
    @Override
    public Genre add(String genreName) {
        Genre genre = new Genre(genreName);
        return genreDao.save(genre);
    }

    @Transactional(readOnly = true)
    @Override
    public Genre findByName(String name) {
        return genreDao.getByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> findAll() {
        return genreDao.getAll();
    }
}
