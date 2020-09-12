package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.jpa.*;
import ru.otus.homework.repository.*;

import java.util.List;

@Service
public class InfoService {

    private final AuthorRepository authorRepo;
    private final GenreRepository genreRepo;
    private final BookRepository bookRepo;
    private final CommentRepository commentRepo;
    private final LinkRepository linkRepo;

    public InfoService(AuthorRepository authorRepo, GenreRepository genreRepo, BookRepository bookRepo, CommentRepository commentRepo, LinkRepository linkRepository) {
        this.authorRepo = authorRepo;
        this.genreRepo = genreRepo;
        this.bookRepo = bookRepo;
        this.commentRepo = commentRepo;
        this.linkRepo = linkRepository;
    }

    public List<Author> outputAuthors() {
        return authorRepo.findAll();
    }

    public List<Genre> outputGenres() {
        return genreRepo.findAll();
    }

    public List<Book> outputBooks() {
        return bookRepo.findAll();
    }

    public List<Comment> outputComments() {
        return commentRepo.findAll();
    }

    public Iterable<Link> outputLinks() {
        return linkRepo.findAll();
    }

}
