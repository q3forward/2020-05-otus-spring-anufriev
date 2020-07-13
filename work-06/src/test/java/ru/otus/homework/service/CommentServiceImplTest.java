package ru.otus.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.CommentDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.utils.exception.BookNotFoundException;
import ru.otus.homework.utils.exception.CommentNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Тест сервиса работы с комментариями")
@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentDao commentDao;
    @Mock
    private BookService bookService;

    @InjectMocks
    private CommentServiceImpl service;

    private Genre genre;
    private Author author;
    private Book book;

    @BeforeEach
    private void setUp() {
        genre = new Genre(1L, "Test genre");
        author = new Author(1L, "Test author");
        book = new Book(1L, "Book title", author, genre);
    }

    @Test
    @DisplayName("тест поиска по Id")
    void findByIdTest() {
        Optional<Comment> comment = Optional.of(new Comment(1L, "Comment text", book));
        given(commentDao.getById(anyLong())).willReturn(comment);

        Comment actual = service.getById(1L);
        assertThat(actual).isNotNull().isEqualToComparingFieldByField(comment.get());
    }

    @Test
    @DisplayName("тест поиска всех записей")
    void findAllTest() {
        Comment comment = new Comment(1L, "Comment text", book);
        Comment comment2 = new Comment(2L, "Comment text 2", book);
        List<Comment> expectedList = Arrays.asList(comment, comment2);
        given(commentDao.getAll()).willReturn(expectedList);

        List<Comment> actualList = service.getAll();
        assertThat(actualList)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(expectedList);
    }

    @Test
    @DisplayName("тест добавления")
    void addTest() throws BookNotFoundException {
        Comment comment = new Comment(1L, "Comment text", book);
        given(commentDao.save(any())).willReturn(comment);
        given(bookService.getById(anyLong())).willReturn(book);

        Comment actual = service.add(1L, "Comment text");
        assertThat(actual)
                .isNotNull()
                .isEqualToComparingFieldByField(comment);
    }

    @Test
    @DisplayName("тест изменения")
    void updateTest() throws BookNotFoundException, CommentNotFoundException {
        long id = 1;

        Optional<Comment> oldComment = Optional.of(new Comment(1L, "Comment text", book));
        given(commentDao.getById(anyLong())).willReturn(oldComment);
        given(bookService.getById(anyLong())).willReturn(book);

        String newText = "New text";
        service.update(id, book.getId(), newText);

        verify(commentDao, times(1)).getById(id);
        verify(commentDao, times(1)).save(any());
    }

    @Test
    @DisplayName("тест успешного удаления")
    void deleteTest() throws CommentNotFoundException {
        given(commentDao.existsById(anyLong())).willReturn(true);
        long id = 1;
        service.delete(id);

        verify(commentDao, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("тест исключения при удалении, если комментарий не найден")
    void deleteExceptionTest() {
        given(commentDao.existsById(anyLong())).willReturn(false);

        Assertions.assertThrows(CommentNotFoundException.class, () -> service.delete(1));
    }
}
