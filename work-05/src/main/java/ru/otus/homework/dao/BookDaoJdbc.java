package ru.otus.homework.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public int count() {
        return jdbcOperations.queryForObject("select count(*) from books", new HashMap(),Integer.class);
    }

    @Override
    public long insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("genre_id", book.getGenre().getId());
        params.addValue("author_id", book.getAuthor().getId());
        jdbcOperations.update("insert into books (title, genre_id, author_id) values (:title, :genre_id, :author_id)", params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Book book) {
        Map<String, Object> params = Map.of(
            "id", book.getId(),
            "title", book.getTitle(),
            "genre_id", book.getGenre().getId(),
            "author_id", book.getAuthor().getId()
        );
        jdbcOperations.update("update books set title=:title, genre_id=:genre_id, author_id=:author_id where id=:id", params);
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return jdbcOperations.queryForObject(
                    "select b.id, b.title, a.id as author_id, a.name as author_name, g.id as genre_id, g.name as genre_name from books b" +
                            "   inner join authors a on b.author_id=a.id" +
                            "   inner join genres g on b.genre_id=g.id" +
                            "   where b.id=:id", params, new BookMapper()
            );
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Book> getAll() {
        return jdbcOperations.query(
                "select b.id, b.title, a.id as author_id, a.name as author_name, g.id as genre_id, g.name as genre_name from books b" +
                        "   inner join authors a on b.author_id=a.id" +
                        "   inner join genres g on b.genre_id=g.id", new BookMapper()
        );
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcOperations.update("delete from books where id= :id", params);
    }

    @Override
    public boolean existsById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        Integer cnt = jdbcOperations.queryForObject("select count(*) from books where id=:id", params, Integer.class);
        return cnt !=null && cnt>0;
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            long genreId = resultSet.getLong("genre_id");
            String genreName = resultSet.getString("genre_name");
            long authorId = resultSet.getLong("author_id");
            String authorName = resultSet.getString("author_name");

            Genre genre = new Genre(genreId, genreName);
            Author author = new Author(authorId, authorName);
            return new Book(id, title, author, genre);
        }
    }
}
