package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тест DAO для работы с комментариями")
@DataJpaTest
@Import({CommentDaoImpl.class})
class CommentDaoImplTest {

    @Autowired
    private CommentDaoImpl dao;
    @Autowired
    private TestEntityManager em;

    @DisplayName("проверка корректности количества")
    @Test
    void countTest() {
        long count = dao.count();
        assertThat(count).isEqualTo(3);
    }

    @DisplayName("проверка корректности добавления")
    @Test
    void insertTest() {
        Author author = new Author();
        Genre genre = new Genre();
        Book book = new Book(1,"Test book", author, genre);
        Comment expected = new Comment("Comment text", book);
        dao.save(expected);
        Comment actual = em.find(Comment.class, expected.getId());
        assertThat(actual).isNotNull().isEqualToComparingOnlyGivenFields(expected, "id", "text");
        assertThat(actual.getBook()).isEqualToComparingFieldByField(expected.getBook());
    }

    @DisplayName("проверка корректности обновления")
    @Test
    void updateTest() {
        Author author = new Author(1,"Test author");
        Genre genre = new Genre(1,"Test genre");
        Book book = new Book(1,"Book title", author, genre);
        Comment expected = new Comment(2,"Updated text", book);
        dao.save(expected);
        Comment actual = em.find(Comment.class, expected.getId());
        assertThat(actual).isNotNull().isEqualToComparingOnlyGivenFields(expected, "id", "text");
        assertThat(actual.getBook()).isEqualToIgnoringGivenFields(expected.getBook(), "$$_hibernate_interceptor");
    }

    @DisplayName("проверка корректности получения по Id")
    @Test
    void getByIdTest() {
        Comment comment = dao.getById(1L).orElse(null);
        assertThat(comment).isNotNull().hasFieldOrPropertyWithValue("text", "Comment");
        assertThat(comment.getBook()).hasFieldOrPropertyWithValue("title", "Book title");
    }

    @DisplayName("проверка корректности получения всех комментариев")
    @Test
    void getAllTest() {
        List<Comment> commentList = dao.getAll();
        assertThat(commentList.size()).isEqualTo(3);
        assertThat(commentList.get(0)).hasFieldOrPropertyWithValue("id", 1L).hasFieldOrPropertyWithValue("text", "Comment");
        assertThat(commentList.get(1)).hasFieldOrPropertyWithValue("id", 2L).hasFieldOrPropertyWithValue("text", "Comment 2");
        assertThat(commentList.get(2)).hasFieldOrPropertyWithValue("id", 3L).hasFieldOrPropertyWithValue("text", "Comment 3");
    }

    @DisplayName("проверка корректности удаления")
    @Test
    void deleteByIdTest() {
        dao.deleteById(3L);
        Comment comment = em.find(Comment.class, 3L);
        assertNull(comment);
    }

    @DisplayName("проверка существования по Id")
    @Test
    void existsByIdTest() {
        assertTrue(dao.existsById(1L));
    }
}
