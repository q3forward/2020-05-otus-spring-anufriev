package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.otus.homework.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест репозитория комментариев")
@DataJpaTest
@EnableJpaRepositories("ru.otus.homework.repository")
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository repo;

    @DisplayName("проверка корректности получения всех комментариев")
    @Test
    void findAllTest() {
        List<Comment> commentList = repo.findAll();
        assertThat(commentList.size()).isEqualTo(3);
        assertThat(commentList.get(0)).hasFieldOrPropertyWithValue("id", 1L).hasFieldOrPropertyWithValue("text", "Comment");
        assertThat(commentList.get(1)).hasFieldOrPropertyWithValue("id", 2L).hasFieldOrPropertyWithValue("text", "Comment 2");
        assertThat(commentList.get(2)).hasFieldOrPropertyWithValue("id", 3L).hasFieldOrPropertyWithValue("text", "Comment 3");
    }

    @DisplayName("проверка корректности получения комментариев для определенной книги")
    @Test
    void getCommentsByBookId() {
        List<Comment> commentList = repo.getCommentsByBookId(1L);
        assertThat(commentList.size()).isEqualTo(2);
        assertThat(commentList.get(0)).hasFieldOrPropertyWithValue("id", 1L).hasFieldOrPropertyWithValue("text", "Comment");
        assertThat(commentList.get(1)).hasFieldOrPropertyWithValue("id", 2L).hasFieldOrPropertyWithValue("text", "Comment 2");
    }

}
