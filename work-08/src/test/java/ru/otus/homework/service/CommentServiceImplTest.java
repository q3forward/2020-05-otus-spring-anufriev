package ru.otus.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.CommentRepository;
import ru.otus.homework.utils.exception.BookNotFoundException;
import ru.otus.homework.utils.exception.CommentNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Тест сервиса работы с комментариями")
@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository repo;
    @Mock
    private BookService bookService;

    @InjectMocks
    private CommentServiceImpl service;

    private Genre genre;
    private Author author;
    private Book book;

    @BeforeEach
    private void setUp() {
        genre = new Genre("1", "Test genre");
        author = new Author("1", "Test author");
        book = new Book("1", "Book title", author, genre);
    }

    @Test
    @DisplayName("тест поиска по Id")
    void findByIdTest() {
        Optional<Comment> comment = Optional.of(new Comment("1", "Comment text", book));
        given(repo.findById(anyString())).willReturn(comment);

        Comment actual = service.getById("1");
        assertThat(actual).isNotNull().isEqualToComparingFieldByField(comment.get());
    }

    @Test
    @DisplayName("тест поиска всех записей")
    void findAllTest() {
        Comment comment = new Comment("1", "Comment text", book);
        Comment comment2 = new Comment("2", "Comment text 2", book);
        List<Comment> expectedList = Arrays.asList(comment, comment2);
        given(repo.findAll()).willReturn(expectedList);

        Iterable<Comment> actualList = service.getAll();
        assertThat(actualList)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(expectedList);
    }

    @Test
    @DisplayName("тест добавления")
    void addTest() throws BookNotFoundException {
        Comment comment = new Comment("1", "Comment text", book);
        given(repo.save(any())).willReturn(comment);
        given(bookService.getById(anyString())).willReturn(book);

        Comment actual = service.add("1", "Comment text");
        assertThat(actual)
                .isNotNull()
                .isEqualToComparingFieldByField(comment);
    }

    @Test
    @DisplayName("тест изменения")
    void updateTest() throws BookNotFoundException, CommentNotFoundException {
        String id = "1";

        Optional<Comment> oldComment = Optional.of(new Comment("1", "Comment text", book));
        given(repo.findById(anyString())).willReturn(oldComment);
        given(bookService.getById(anyString())).willReturn(book);

        String newText = "New text";
        service.update(id, book.getId(), newText);

        verify(repo, times(1)).findById(id);
        verify(repo, times(1)).save(any());
    }

    @Test
    @DisplayName("тест успешного удаления")
    void deleteTest() throws CommentNotFoundException {
        given(repo.existsById(anyString())).willReturn(true);
        String id = "1";
        service.delete(id);

        verify(repo, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("тест исключения при удалении, если комментарий не найден")
    void deleteExceptionTest() {
        given(repo.existsById(anyString())).willReturn(false);

        Assertions.assertThrows(CommentNotFoundException.class, () -> service.delete("1"));
    }
}
