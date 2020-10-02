package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Genre")
class GenreTest {

    @DisplayName("успешно создается через конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        Genre genre = new Genre("Тестовый жанр");
        Genre genre2 = new Genre("1", "Тестовый жанр2");

        assertEquals("Тестовый жанр", genre.getName());
        assertEquals("1", genre2.getId());
        assertEquals("Тестовый жанр2", genre2.getName());
    }
}
