package ru.otus.homework.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public int count() {
        return jdbcOperations.queryForObject("select count(*) from authors", new HashMap(),Integer.class);
    }

    @Override
    public long insert(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", author.getName());
        jdbcOperations.update("insert into authors (name) values (:name)", parameters, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Author author) {
        Map<String, Object> params = Map.of(
                "id", author.getId(),
                "name", author.getName());
        jdbcOperations.update("update authors set name=:name where id=:id", params);
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return jdbcOperations.queryForObject(
                    "select * from authors where id = :id", params, new AuthorDaoJdbc.AuthorMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Author> getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name.toLowerCase());
        try {
            return jdbcOperations.query("select * from authors where lower(name)=:name", params, new AuthorDaoJdbc.AuthorMapper());
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Author> getAll() {
        return jdbcOperations.query("select * from authors", new AuthorDaoJdbc.AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcOperations.update("delete from authors where id= :id", params);
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
