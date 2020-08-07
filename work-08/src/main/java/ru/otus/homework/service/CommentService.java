package ru.otus.homework.service;

import ru.otus.homework.domain.Comment;
import ru.otus.homework.utils.exception.BookNotFoundException;
import ru.otus.homework.utils.exception.CommentNotFoundException;

import java.util.List;

public interface CommentService {
    Comment add(String bookId, String text) throws BookNotFoundException;
    void update(String commentId, String bookId, String text) throws CommentNotFoundException, BookNotFoundException;
    void delete(String commentId) throws CommentNotFoundException;
    void deleteAllByBookId(String bookId);
    List<Comment> getAll();
    List<String> getCommentIdsByBookId(String bookId);
    Comment getById(String commentId);
    List<Comment> getBookComments(String bookId) throws BookNotFoundException;
}
