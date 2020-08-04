package ru.otus.homework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.domain.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, String> {

    List<Comment> findAll();

    List<Comment> getCommentsByBookId(String bookId);
}
