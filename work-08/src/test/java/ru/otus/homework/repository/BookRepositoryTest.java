package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Book;
import ru.otus.homework.mongock.MongockConfig;

import java.util.List;

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
        List<Book> bookList = repo.findAll();
        assertThat(bookList.size()).isEqualTo(2);
        assertThat(bookList.get(0)).hasFieldOrPropertyWithValue("id", "1").hasFieldOrPropertyWithValue("title", "Book title");
        assertThat(bookList.get(1)).hasFieldOrPropertyWithValue("id", "2").hasFieldOrPropertyWithValue("title", "Book title 2");
    }

}
