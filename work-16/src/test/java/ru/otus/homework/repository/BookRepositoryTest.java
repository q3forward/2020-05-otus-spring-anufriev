package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.otus.homework.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест репозитория книг")
@DataJpaTest
@EnableJpaRepositories("ru.otus.homework.repository")
class BookRepositoryTest {

    @Autowired
    private BookRepository repo;

    @DisplayName("проверка корректности получения всех книг")
    @Test
    void getAllTest() {
        List<Book> bookList = repo.findAll();
        assertThat(bookList.size()).isEqualTo(2);
        assertThat(bookList.get(0)).hasFieldOrPropertyWithValue("id", 1L).hasFieldOrPropertyWithValue("title", "Book title");
        assertThat(bookList.get(1)).hasFieldOrPropertyWithValue("id", 2L).hasFieldOrPropertyWithValue("title", "Book title 2");
    }

}
