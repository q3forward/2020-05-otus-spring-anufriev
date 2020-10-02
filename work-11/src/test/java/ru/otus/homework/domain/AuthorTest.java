package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Author")
class AuthorTest {

    @DisplayName("успешно создается через конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        Author author = new Author("Тестовый автор");
        Author author2 = new Author("123", "Тестовый автор2");
        assertEquals("Тестовый автор", author.getName());
        assertEquals("123", author2.getId());
        assertEquals("Тестовый автор2", author2.getName());
    }
}
