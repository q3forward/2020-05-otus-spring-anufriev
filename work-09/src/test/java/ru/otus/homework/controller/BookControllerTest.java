package ru.otus.homework.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;

    @Test
    @DisplayName("тест контроллера списка книг")
    public void listBookTest() throws Exception {
        List<Book> bookList = Arrays.asList(new Book(101L, "Test title 1", new Author(), new Genre()),  new Book(102L, "Test title 2", new Author(), new Genre()));
        given(bookService.getAll()).willReturn(bookList);

        mvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("listBook"))
                .andExpect(content().string(containsString("Test title 1")))
                .andExpect(content().string(containsString("Test title 2")));
    }

    @Test
    @DisplayName("тест гет-контроллера добавления книг")
    void addBookTest() throws Exception {
        mvc.perform(get("/addBook"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("addBook"));
    }

    @Test
    @DisplayName("тест гет-контроллера изменения книги")
    void editBookTest() throws Exception {
        Book book = new Book(101L, "Test title", new Author(), new Genre());
        given(bookService.getById(anyLong())).willReturn(book);

        mvc.perform(get("/editBook").param("id", "101"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("editBook"))
                .andExpect(content().string(containsString("101")))
                .andExpect(content().string(containsString("Test title")));
    }

    @Test
    @DisplayName("тест пост-контроллера изменения книги")
    void postEditBookTest() throws Exception {

        mvc.perform(post("/editBook")
                .param("id", "101")
                .param("title","Test title")
                .param("author", new Author().toString())
                .param("genre", new Genre().toString()))
            .andDo(print())
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("тест контроллера удаления книги")
    void deleteCommentTest() throws Exception {
        mvc.perform(get("/deleteBook").param("id", "101"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
