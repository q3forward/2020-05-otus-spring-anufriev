package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.homework.domain.Author;
import ru.otus.homework.mongock.MongockConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест репозитория авторов")
@DataMongoTest
@Import(MongockConfig.class)
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repo;

    @DisplayName("проверка корректности получения по имени")
    @Test
    void getByNameTest() {
        List<Author> authorList = repo.getByName("Test author");
        assertThat(authorList.size()).isEqualTo(1);
        assertThat(authorList.get(0)).hasFieldOrPropertyWithValue("id", "1").hasFieldOrPropertyWithValue("name", "Test author");
    }
}
