package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.otus.homework.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест репозитория жанров")
@DataJpaTest
@EnableJpaRepositories("ru.otus.homework.repository")
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository repo;

    @DisplayName("проверка корректности получения по названию")
    @Test
    void getByNameTest() {
        Genre genre = repo.getByName("Test genre");
        assertThat(genre).isNotNull().hasFieldOrPropertyWithValue("name", "Test genre");
    }

}
