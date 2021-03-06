package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.homework.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.book b join fetch b.genre join fetch b.author")
    List<Comment> findAll();

    @Query("select c from Comment c join fetch c.book b join fetch b.genre join fetch b.author where b.id=:bookId")
    List<Comment> getCommentsByBookId(Long bookId);

    @Query("select c from Comment c join fetch c.book b join fetch b.genre join fetch b.author where c.id=:commentId")
    Optional<Comment> findById(Long commentId);

    void deleteByBookId(Long bookId);
}
