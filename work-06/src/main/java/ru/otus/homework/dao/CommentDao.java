package ru.otus.homework.dao;

import ru.otus.homework.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    long count();
    Comment save(Comment comment);
    Optional<Comment> getById(long id);
    List<Comment> getAll();
    List<Comment> getCommentsByBookId(long bookId);
    void deleteById(long id);
    boolean existsById(long id);
}
