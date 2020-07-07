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
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.utils.exception.BookNotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Тест сервиса работы с книгами")
@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookDao bookDao;
    @Mock
    private AuthorService authorService;
    @Mock
    private GenreService genreService;

    @InjectMocks
    private BookServiceImpl service;

    private Genre genre;
    private Author author;

    @BeforeEach
    private void setUp() {
        genre = new Genre(1L, "Test genre");
        author = new Author(1L, "Test author");
    }

    @Test
    @DisplayName("тест поиска по Id")
    void findByIdTest() {
        Book Book = new Book(1L, "Test Book", author, genre);
        given(bookDao.getById(anyLong())).willReturn(Book);

        Book actual = service.getById(1L);
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualToComparingFieldByField(Book);
    }

    @Test
    @DisplayName("тест поиска всех записей")
    void findAllTest() {
        Book book = new Book(1L, "Test Book", author, genre);
        Book book2 = new Book(2L, "Test Book", author, genre);
        List<Book> expectedList = Arrays.asList(book, book2);
        given(bookDao.getAll()).willReturn(expectedList);

        List<Book> actualList = service.getAll();
        assertThat(actualList)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(expectedList);
    }

    @Test
    @DisplayName("тест добавления")
    void addTest() {
        Book book = new Book(1L, "Test Book", author, genre);
        List<Author> authorList = Arrays.asList(author);
        given(bookDao.insert(any())).willReturn(1L);
        given(authorService.findByName(any())).willReturn(authorList);
        given(genreService.findByName(any())).willReturn(genre);

        Book actual = service.add("Test Book", "Test author", "Test genre");
        assertThat(actual)
                .isNotNull()
                .isEqualToComparingFieldByField(book);
    }

    @Test
    @DisplayName("тест изменения")
    void updateTest() throws BookNotFoundException {
        long id = 1;

        Book oldBook = new Book(id, "Old title", author, genre);
        List<Author> authorList = Arrays.asList(author);
        given(bookDao.getById(anyLong())).willReturn(oldBook);
        given(authorService.findByName(any())).willReturn(authorList);
        given(genreService.findByName(any())).willReturn(genre);

        String newTitle = "New title";
        String newAuthorName = "Test author";
        String newGenreName = "Test genre";
        Book newBook = new Book(id, newTitle, author, genre);
        service.update(id, newTitle, newAuthorName, newGenreName);

        verify(bookDao, times(1)).getById(id);
        verify(bookDao, times(1)).update(any());
    }

    @Test
    @DisplayName("тест успешного удаления")
    void deleteTest() throws BookNotFoundException {
        Book book = new Book(1L, "Test Book", author, genre);
        given(bookDao.existsById(anyLong())).willReturn(true);
        long id = 1;
        service.delete(id);

        verify(bookDao, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("тест исключения при удалении, если книга не найдена")
    void deleteExceptionTest() throws BookNotFoundException {
        given(bookDao.existsById(anyLong())).willReturn(false);

        Assertions.assertThrows(BookNotFoundException.class, () -> service.delete(1));
    }
}
