package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.domain.Author;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public Author add(String authorName) {
        Author author = new Author(authorName);
        author.setId(authorDao.insert(author));
        return author;
    }

    @Override
    public Author findById(long authorId) {
        return authorDao.getById(authorId);
    }

    @Override
    public List<Author> findByName(String authorName) {
        return authorDao.getByName(authorName);
    }

    @Override
    public List<Author> findAll() {
        return authorDao.getAll();
    }
}
