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

@DisplayName("Класс Comment")
@ExtendWith(MockitoExtension.class)
public class CommentTest {

    @Mock
    private Book book;

    @BeforeEach
    void setUp() {
        given(book.getId()).willReturn(1L);
        given(book.getTitle()).willReturn("Book title");
    }

    @DisplayName("успешно создается через конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        Comment comment = new Comment(1L,"Commentary text", book);

        assertEquals(1L, comment.getId());
        assertEquals("Commentary text", comment.getText());
        Book actualBook = comment.getBook();
        assertEquals(1L, actualBook.getId());
        assertEquals("Book title", actualBook.getTitle());
    }
}
