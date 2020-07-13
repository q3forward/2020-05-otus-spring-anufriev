package ru.otus.homework.service;

import ru.otus.homework.domain.Comment;
import ru.otus.homework.utils.exception.BookNotFoundException;
import ru.otus.homework.utils.exception.CommentNotFoundException;

import java.util.List;

public interface CommentService {
    Comment add(long bookId, String text) throws BookNotFoundException;
    void update(long commentId, long bookId, String text) throws CommentNotFoundException, BookNotFoundException;
    void delete(long commentId) throws CommentNotFoundException;
    List<Comment> getAll();
    Comment getById(long commentId);
    List<Comment> getBookComments(long bookId) throws BookNotFoundException;
}
