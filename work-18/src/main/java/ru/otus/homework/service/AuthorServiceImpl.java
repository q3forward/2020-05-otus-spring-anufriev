package ru.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.AuthorRepository;


import java.util.ArrayList;
import java.util.Collections;
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

    @HystrixCommand(fallbackMethod = "findById_Fallback")
    @Transactional(readOnly = true)
    @Override
    public Optional<Author> findById(long authorId) {
        return authorRepo.findById(authorId);
    }

    @HystrixCommand(fallbackMethod = "findByNameAuthor_Fallback")
    @Transactional(readOnly = true)
    @Override
    public List<Author> findByName(String authorName) {
        return authorRepo.getByName(authorName);
    }

    @HystrixCommand(fallbackMethod = "findAllAuthor_Fallback")
    @Transactional(readOnly = true)
    @Override
    public List<Author> findAll() {
        return authorRepo.findAll();
    }

    private Author findById_Fallback(long authorId) {
        System.out.println("Something going wrong on add author");
        return new Author("N/A");
    }

    private List<Author> findByNameAuthor_Fallback(String authorName) {
        return new ArrayList();
    }

    private List<Author> findAllAuthor_Fallback() {
        return new ArrayList<Author>();
    }
}
