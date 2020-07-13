package ru.otus.homework.dao;

import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Comment;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDaoImpl implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        return em.createQuery("select count(c) from Comment c", Long.class).getSingleResult();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId()==0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public Optional<Comment> getById(long id) {
        TypedQuery<Comment> query = em.createQuery(
                "select c from Comment c where c.id = :id", Comment.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Comment> getAll() {
        return em.createQuery("select c from Comment c", Comment.class).getResultList();
    }

    @Override
    public List<Comment> getCommentsByBookId(long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c join c.book b where b.id=:bookId", Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query =  em.createQuery("delete from Comment c where c.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public boolean existsById(long id) {
        Long cnt = em.createQuery("select count(c) from Comment c", Long.class).getSingleResult();
        return cnt !=null && cnt>0;
    }

}
