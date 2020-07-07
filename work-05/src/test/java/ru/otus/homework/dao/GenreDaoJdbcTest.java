package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Тест DAO для работы с жанрами")
@JdbcTest
@Import({GenreDaoJdbc.class})
public class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc dao;

    @DisplayName("проверка корректности количества")
    @Test
    void countTest() {
        int count = dao.count();
        assertThat(3).isEqualTo(count);
    }

    @DisplayName("проверка корректности добавления")
    @Test
    void insertTest() {
        Genre expected = new Genre("Added genre");
        long addedId = dao.insert(expected);
        expected.setId(addedId);
        Genre actual = dao.getById(addedId);
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @DisplayName("проверка корректности получения по Id")
    @Test
    void getByIdTest() {
        Genre genre = dao.getById(1L);
        assertThat(genre).hasFieldOrPropertyWithValue("name", "Test genre");
    }

    @DisplayName("проверка корректности получения по названию")
    @Test
    void getByNameTest() {
        Genre genre = dao.getByName("Test genre");
        assertThat(genre).hasFieldOrPropertyWithValue("name", "Test genre");
    }

    @DisplayName("проверка корректности получения всех жанров")
    @Test
    void getAllTest() {
        List<Genre> genreList = dao.getAll();
        assertThat(genreList.size()).isEqualTo(3);
        assertThat(genreList.get(0)).hasFieldOrPropertyWithValue("id", 1L).hasFieldOrPropertyWithValue("name", "Test genre");
        assertThat(genreList.get(1)).hasFieldOrPropertyWithValue("id", 2L).hasFieldOrPropertyWithValue("name", "Test genre 2");
        assertThat(genreList.get(2)).hasFieldOrPropertyWithValue("id", 3L).hasFieldOrPropertyWithValue("name", "Test genre 3");
    }

    @DisplayName("проверка корректности удаления")
    @Test
    void deleteByIdTest() {
        dao.deleteById(3L);
        Genre genre = dao.getById(3L);
        assertNull(genre);
    }
}
