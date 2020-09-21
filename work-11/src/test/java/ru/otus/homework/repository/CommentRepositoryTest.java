package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;
import ru.otus.homework.mongock.MongockConfig;

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
        StepVerifier.create(commentRepo.findAll())
                .assertNext(comment -> {
                    assertThat(comment).isNotNull().hasFieldOrPropertyWithValue("id", "1").hasFieldOrPropertyWithValue("text", "Comment");
                })
                .assertNext(comment -> {
                    assertThat(comment).isNotNull().hasFieldOrPropertyWithValue("id", "2").hasFieldOrPropertyWithValue("text", "Comment 2");
                })
                .assertNext(comment -> {
                    assertThat(comment).isNotNull().hasFieldOrPropertyWithValue("id", "3").hasFieldOrPropertyWithValue("text", "Comment 3");
                })
                .expectComplete()
                .verify();
    }

    @DisplayName("проверка корректности получения комментариев для определенной книги")
    @Test
    void getCommentsByBookIdTest() {
        StepVerifier.create(commentRepo.getCommentsByBookId("1"))
                .assertNext(comment -> {
                    assertThat(comment).isNotNull().hasFieldOrPropertyWithValue("id", "1").hasFieldOrPropertyWithValue("text", "Comment");
                })
                .assertNext(comment -> {
                    assertThat(comment).isNotNull().hasFieldOrPropertyWithValue("id", "2").hasFieldOrPropertyWithValue("text", "Comment 2");
                })
                .expectComplete()
                .verify();
    }
}
