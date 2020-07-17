package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Тест DAO для работы с жанрами")
@DataJpaTest
@Import({GenreDaoImpl.class})
class GenreDaoImplTest {

    @Autowired
    private GenreDaoImpl dao;
    @Autowired
    private TestEntityManager em;

    @DisplayName("проверка корректности количества")
    @Test
    void countTest() {
        long count = dao.count();
        assertThat(count).isEqualTo(3L);
    }

    @DisplayName("проверка корректности добавления")
    @Test
    void saveTest() {
        Genre expected = new Genre("Added genre");
        dao.save(expected);
        Genre actual = em.find(Genre.class,expected.getId());
        assertThat(actual).isNotNull().isEqualToComparingFieldByField(expected);
    }

    @DisplayName("проверка корректности получения по Id")
    @Test
    void getByIdTest() {
        Genre genre = dao.getById(1L).orElse(null);
        assertThat(genre).isNotNull().hasFieldOrPropertyWithValue("name", "Test genre");
    }

    @DisplayName("проверка корректности получения по названию")
    @Test
    void getByNameTest() {
        Genre genre = dao.getByName("Test genre");
        assertThat(genre).isNotNull().hasFieldOrPropertyWithValue("name", "Test genre");
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
        Genre genre = em.find(Genre.class, 3L);
        assertNull(genre);
    }
}
