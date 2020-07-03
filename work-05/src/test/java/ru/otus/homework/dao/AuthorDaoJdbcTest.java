package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Тест DAO для работы с авторами")
@JdbcTest
@Import({AuthorDaoJdbc.class})
public class AuthorDaoJdbcTest {
    @Autowired
    private AuthorDaoJdbc dao;

    @DisplayName("проверка корректности количества")
    @Test
    void countTest() {
        int count = dao.count();
        assertThat(3).isEqualTo(count);
    }

    @DisplayName("проверка корректности добавления")
    @Test
    void insertTest() {
        Author expected = new Author("Added author");
        long addedId = dao.insert(expected);
        expected.setId(addedId);
        Author actual = dao.getById(addedId);
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @DisplayName("проверка корректности получения по Id")
    @Test
    void getByIdTest() {
        Author author = dao.getById(1L);
        assertThat(author).hasFieldOrPropertyWithValue("name", "Test author");
    }

    @DisplayName("проверка корректности получения по названию")
    @Test
    void getByNameTest() {
        List<Author> authorList = dao.getByName("Test author");
        assertThat(1).isEqualTo(authorList.size());
        assertThat(authorList.get(0)).hasFieldOrPropertyWithValue("name", "Test author");
    }

    @DisplayName("проверка корректности получения всех авторов")
    @Test
    void getAllTest() {
        List<Author> authorList = dao.getAll();
        assertThat(3).isEqualTo(authorList.size());
        assertThat(authorList.get(0)).hasFieldOrPropertyWithValue("id", 1L).hasFieldOrPropertyWithValue("name", "Test author");
        assertThat(authorList.get(1)).hasFieldOrPropertyWithValue("id", 2L).hasFieldOrPropertyWithValue("name", "Test author 2");
        assertThat(authorList.get(2)).hasFieldOrPropertyWithValue("id", 3L).hasFieldOrPropertyWithValue("name", "Test author 3");
    }

    @DisplayName("проверка корректности удаления")
    @Test
    void deleteByIdTest() {
        dao.deleteById(3L);
        Author author = dao.getById(3L);
        assertNull(author);
    }
}
