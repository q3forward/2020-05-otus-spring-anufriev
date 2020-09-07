package ru.otus.homework.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.service.*;
import ru.otus.homework.service.BookService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import(UserServiceImpl.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private UserServiceImpl userServiceImpl;

    @Test
    @DisplayName("тест рест контроллера списка книг")
    public void getBooksTest() throws Exception {
        given(bookService.getAll()).willReturn(Arrays.asList(new Book(1L, "Book1", new Author(), new Genre()), new Book(2L, "Book2", new Author(), new Genre())));

        mvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]",hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Book1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Book2")));
    }

    @Test
    @DisplayName("тест рест контроллера получения книги по id")
    public void getBookByIdTest() throws Exception {
        given(bookService.getById(anyLong())).willReturn(new Book(1L, "Book1", new Author(), new Genre()));

        mvc.perform(get("/api/book/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Book1")));
    }

    @Test
    @DisplayName("тест рест контроллера добавления книги")
    public void addBookTest() throws Exception {
        given(bookService.getById(anyLong())).willReturn(new Book(1L, "Book1", new Author(), new Genre()));

        mvc.perform(post("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"BookTitle\", \"author\":{}, \"genre\":{} }"))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("тест рест контроллера изменения книги")
    public void editBookTest() throws Exception {
        mvc.perform(put("/api/book/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"BookTitle\", \"author\":{}, \"genre\":{} }"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("тест рест контроллера удаления книги")
    public void deleteBookTest() throws Exception {
        mvc.perform(delete("/api/book/100"))
                .andExpect(status().isOk());
    }
}
