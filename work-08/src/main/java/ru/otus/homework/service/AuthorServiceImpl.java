package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.repository.AuthorRepository;


import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepo;

    public AuthorServiceImpl(AuthorRepository authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Override
    @Transactional
    public Author add(String authorName) {
        Author author = new Author(authorName);
        return authorRepo.save(author);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> findById(String authorId) {
        return authorRepo.findById(authorId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> findByName(String authorName) {
        return authorRepo.getByName(authorName);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<Author> findAll() {
        return authorRepo.findAll();
    }
}
