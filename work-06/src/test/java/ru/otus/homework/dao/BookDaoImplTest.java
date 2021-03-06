package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тест DAO для работы с книгами")
@DataJpaTest
@Import({BookDaoImpl.class})
public class BookDaoImplTest {

    @Autowired
    private BookDaoImpl dao;
    @Autowired
    private TestEntityManager em;

    @DisplayName("проверка корректности количества")
    @Test
    void countTest() {
        long count = dao.count();
        assertThat(2).isEqualTo(count);
    }

    @DisplayName("проверка корректности добавления")
    @Test
    void insertTest() {
        Author author = new Author(1,"Test author");
        Genre genre = new Genre(1,"Test genre");
        Book expected = new Book("Added book", author, genre);
        dao.save(expected);
        Book actual = em.find(Book.class,expected.getId());
        assertThat(actual).isNotNull().isEqualToComparingOnlyGivenFields(expected, "id", "title");
        assertThat(actual.getAuthor()).isEqualToComparingFieldByField(expected.getAuthor());
        assertThat(actual.getGenre()).isEqualToComparingFieldByField(expected.getGenre());
    }

    @DisplayName("проверка корректности получения по Id")
    @Test
    void getByIdTest() {
        Book book = dao.getById(1L).orElse(null);
        assertThat(book).isNotNull().hasFieldOrPropertyWithValue("title", "Book title");
        assertThat(book.getAuthor()).hasFieldOrPropertyWithValue("name", "Test author");
        assertThat(book.getGenre()).hasFieldOrPropertyWithValue("name", "Test genre");
    }

    @DisplayName("проверка корректности получения всех книг")
    @Test
    void getAllTest() {
        List<Book> bookList = dao.getAll();
        assertThat(2).isEqualTo(bookList.size());
        assertThat(bookList.get(0)).hasFieldOrPropertyWithValue("id", 1L).hasFieldOrPropertyWithValue("title", "Book title");
        assertThat(bookList.get(1)).hasFieldOrPropertyWithValue("id", 2L).hasFieldOrPropertyWithValue("title", "Book title 2");
    }

    @DisplayName("проверка корректности удаления")
    @Test
    void deleteByIdTest() {
        dao.deleteById(3L);
        Book book = em.find(Book.class, 3L);
        assertNull(book);
    }

    @DisplayName("проверка существования по Id")
    @Test
    void existsByIdTest() {
        assertTrue(dao.existsById(1L));
    }
}
