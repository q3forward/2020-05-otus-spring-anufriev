package ru.otus.homework.dao;

import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;

import javax.persistence.*;
import java.util.*;

@Repository
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        return em.createQuery("select count(b) from Book b", Long.class).getSingleResult();
    }

    @Override
    public Book save(Book book) {
        if (book.getId()==0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> getAll() {
        return em.createQuery("select b from Book b join fetch b.author join fetch b.genre", Book.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query =  em.createQuery("delete from Book b where b.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public boolean existsById(long id) {
        Long cnt = em.createQuery("select count(b) from Book b", Long.class).getSingleResult();
        return cnt !=null && cnt>0;
    }

}
