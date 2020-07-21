package ru.otus.homework.dao;

import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Genre;

import javax.persistence.*;
import java.util.*;

@Repository
public class GenreDaoImpl implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        return em.createQuery("select count(g) from Genre g", Long.class).getSingleResult();
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId()==0) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public Genre getByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name=:name", Genre.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Genre> getAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query =  em.createQuery("delete from Genre g where g.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
