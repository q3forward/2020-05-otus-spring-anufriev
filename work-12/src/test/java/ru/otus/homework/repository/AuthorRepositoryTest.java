package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.otus.homework.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест репозитория авторов")
@DataJpaTest
@EnableJpaRepositories("ru.otus.homework.repository")
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repo;

    @DisplayName("проверка корректности получения по имени")
    @Test
    void getByNameTest() {
        List<Author> authorList = repo.getByName("Test author");
        assertThat(1).isEqualTo(authorList.size());
        assertThat(authorList.get(0)).hasFieldOrPropertyWithValue("name", "Test author");
    }

}
