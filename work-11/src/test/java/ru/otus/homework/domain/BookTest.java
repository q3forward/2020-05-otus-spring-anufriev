package ru.otus.homework.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс Book")
@ExtendWith(MockitoExtension.class)
class BookTest {

    @Mock
    private Author author;
    @Mock
    private Genre genre;

    @BeforeEach
    void setUp() {
        given(author.getId()).willReturn("1");
        given(author.getName()).willReturn("Тестовый автор");
        given(genre.getId()).willReturn("2");
        given(genre.getName()).willReturn("Тестовый жанр");
    }

    @DisplayName("успешно создается через конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        Book book = new Book("1","Название книги", author, genre);

        assertEquals("1", book.getId());
        assertEquals("Название книги", book.getTitle());
        assertThat(book.getAuthor()).isEqualToComparingFieldByField(author);
        assertThat(book.getGenre()).isEqualToComparingFieldByField(genre);
    }
}
