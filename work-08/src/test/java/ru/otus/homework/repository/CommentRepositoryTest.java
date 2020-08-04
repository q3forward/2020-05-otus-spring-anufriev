package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.mongock.MongockConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест репозитория комментариев")
@DataMongoTest
@Import(MongockConfig.class)
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepo;

    @DisplayName("проверка корректности получения всех комментариев")
    @Test
    void findAllTest() {
        List<Comment> commentList = commentRepo.findAll();
        assertThat(commentList.size()).isEqualTo(3);
        assertThat(commentList.get(0)).hasFieldOrPropertyWithValue("id", "1").hasFieldOrPropertyWithValue("text", "Comment");
        assertThat(commentList.get(1)).hasFieldOrPropertyWithValue("id", "2").hasFieldOrPropertyWithValue("text", "Comment 2");
        assertThat(commentList.get(2)).hasFieldOrPropertyWithValue("id", "3").hasFieldOrPropertyWithValue("text", "Comment 3");
    }

    @DisplayName("проверка корректности получения комментариев для определенной книги")
    @Test
    void getCommentsByBookId() {
        List<Comment> commentList = commentRepo.getCommentsByBookId("1");

        assertThat(commentList.size()).isEqualTo(2);
        assertThat(commentList.get(0)).hasFieldOrPropertyWithValue("id", "1").hasFieldOrPropertyWithValue("text", "Comment");
        assertThat(commentList.get(1)).hasFieldOrPropertyWithValue("id", "2").hasFieldOrPropertyWithValue("text", "Comment 2");
    }

}
