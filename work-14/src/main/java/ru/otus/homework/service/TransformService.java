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
public class TransformService {

    private final AuthorRepository authorRepo;
    private final GenreRepository genreRepo;
    private final BookRepository bookRepo;
    private final CommentRepository commentRepo;
    private final LinkRepository linkRepo;

    public TransformService(AuthorRepository authorRepo, GenreRepository genreRepo, BookRepository bookRepo, CommentRepository commentRepo, LinkRepository linkRepo) {
        this.authorRepo = authorRepo;
        this.genreRepo = genreRepo;
        this.bookRepo = bookRepo;
        this.commentRepo = commentRepo;
        this.linkRepo = linkRepo;
    }

    public Author getJpaAuthor(MongoAuthor author) {
        return new Author(author.getName());
    }

    public Genre getJpaGenre(MongoGenre genre) {
        return new Genre(genre.getName());
    }

    public Book getJpaBook(MongoBook book) {
        Long authorId = linkRepo.findByMongoIdAndClassName(book.getAuthor().getId(),"Author").getJpaId();
        Long genreId = linkRepo.findByMongoIdAndClassName(book.getGenre().getId(),"Genre").getJpaId();
        return new Book(book.getTitle(), authorRepo.findById(authorId).get(), genreRepo.findById(genreId).get());
    }

    public Comment getJpaComment(MongoComment comment) {
        Long bookId = linkRepo.findByMongoIdAndClassName(comment.getBook().getId(),"Book").getJpaId();
        return new Comment(comment.getText(), bookRepo.findById(bookId).get());
    }
}