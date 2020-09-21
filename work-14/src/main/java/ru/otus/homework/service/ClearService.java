package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.jpa.Author;
import ru.otus.homework.domain.jpa.Book;
import ru.otus.homework.domain.jpa.Comment;
import ru.otus.homework.domain.jpa.Genre;
import ru.otus.homework.domain.mongo.MongoAuthor;
import ru.otus.homework.domain.mongo.MongoBook;
import ru.otus.homework.domain.mongo.MongoComment;
import ru.otus.homework.domain.mongo.MongoGenre;
import ru.otus.homework.repository.*;

@Service
public class ClearService {

    private final AuthorRepository authorRepo;
    private final GenreRepository genreRepo;
    private final BookRepository bookRepo;
    private final CommentRepository commentRepo;
    private final LinkRepository linkRepo;

    public ClearService(AuthorRepository authorRepo, GenreRepository genreRepo, BookRepository bookRepo, CommentRepository commentRepo, LinkRepository linkRepo) {
        this.authorRepo = authorRepo;
        this.genreRepo = genreRepo;
        this.bookRepo = bookRepo;
        this.commentRepo = commentRepo;
        this.linkRepo = linkRepo;
    }

    public void clearTargetTables() {
        commentRepo.deleteAll();
        bookRepo.deleteAll();
        genreRepo.deleteAll();
        authorRepo.deleteAll();
        linkRepo.deleteAll();
    }
}
