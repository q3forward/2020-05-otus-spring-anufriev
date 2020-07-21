package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.domain.Author;


import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    @Transactional
    public Author add(String authorName) {
        Author author = new Author(authorName);
        return authorDao.save(author);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> findById(long authorId) {
        return authorDao.getById(authorId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> findByName(String authorName) {
        return authorDao.getByName(authorName);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> findAll() {
        return authorDao.getAll();
    }
}
