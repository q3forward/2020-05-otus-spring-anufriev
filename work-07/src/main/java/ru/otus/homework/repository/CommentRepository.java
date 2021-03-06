package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.domain.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.book b join fetch b.genre join fetch b.author")
    List<Comment> findAll();

    List<Comment> getCommentsByBookId(Long bookId);
}
