package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.homework.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.book b join fetch b.genre join fetch b.author")
    List<Comment> findAll();

    List<Comment> getCommentsByBookId(Long bookId);

    void deleteByBookId(Long bookId);
}
