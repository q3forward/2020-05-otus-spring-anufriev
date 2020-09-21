package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;
import ru.otus.homework.mongock.MongockConfig;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест репозитория книг")
@DataMongoTest
@Import(MongockConfig.class)
class BookRepositoryTest {

    @Autowired
    private BookRepository repo;

    @DisplayName("проверка корректности получения всех книг")
    @Test
    void getAllTest() {
        StepVerifier.create(repo.findAll())
                .assertNext(book -> {
                    assertThat(book).isNotNull().hasFieldOrPropertyWithValue("id", "1").hasFieldOrPropertyWithValue("title", "Book title");
                })
                .assertNext(book -> {
                    assertThat(book).isNotNull().hasFieldOrPropertyWithValue("id", "2").hasFieldOrPropertyWithValue("title", "Book title 2");
                })
                .expectComplete()
                .verify();
    }

}
