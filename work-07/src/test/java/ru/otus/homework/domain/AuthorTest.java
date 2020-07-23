package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Author")
public class AuthorTest {

    @DisplayName("успешно создается через конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        Author author = new Author("Тестовый автор");
        Author author2 = new Author(1L, "Тестовый автор2");
        assertEquals("Тестовый автор", author.getName());
        assertEquals(1, author2.getId());
        assertEquals("Тестовый автор2", author2.getName());
    }
}
