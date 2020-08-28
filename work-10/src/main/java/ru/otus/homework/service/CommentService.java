package ru.otus.homework.service;

import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.utils.exception.BookNotFoundException;
import ru.otus.homework.utils.exception.CommentNotFoundException;

import java.util.List;

public interface CommentService {
    Comment add(Book book, String text);
    void update(long commentId, Book book, String text) throws CommentNotFoundException;
    void delete(long commentId) throws CommentNotFoundException;
    void deleteAllByBookId(long bookId);
    List<Comment> getAll();
    Comment getById(long commentId);
    List<Comment> getBookComments(long bookId) throws BookNotFoundException;
}
