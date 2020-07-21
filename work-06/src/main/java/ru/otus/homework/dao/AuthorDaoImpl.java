package ru.otus.homework.dao;

import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;

import javax.persistence.*;
import java.util.*;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        return em.createQuery("select count(a) from Author a", Long.class).getSingleResult();
    }

    @Override
    public Author save(Author author) {
        if (author.getId()==0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public Optional<Author> getById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> getByName(String name) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.name=:name", Author.class);
        query.setParameter("name", name);
        try {
            return query.getResultList();
        } catch(NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Author> getAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query =  em.createQuery("delete from Author a where a.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
