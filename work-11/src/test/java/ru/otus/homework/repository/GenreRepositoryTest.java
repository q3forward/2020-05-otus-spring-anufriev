package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.mongock.MongockConfig;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест репозитория жанров")
@DataMongoTest
@Import(MongockConfig.class)
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository repo;

    @DisplayName("проверка корректности получения по названию")
    @Test
    void getByNameTest() {
        Mono<Genre> genreMono = repo.findByName("Test genre");

        StepVerifier.create(genreMono)
                .assertNext(genre -> {
                    assertThat(genre).isNotNull().hasFieldOrPropertyWithValue("id", "1").hasFieldOrPropertyWithValue("name", "Test genre");
                })
                .expectComplete()
                .verify();
    }



}
