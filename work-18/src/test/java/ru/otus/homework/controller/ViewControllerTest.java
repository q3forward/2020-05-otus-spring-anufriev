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
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ViewController.class)
public class ViewControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("тест контроллера списка книг")
    public void listBookTest() throws Exception {
        List<Book> bookList = Arrays.asList(new Book(101L, "Test title 1", new Author(), new Genre()),  new Book(102L, "Test title 2", new Author(), new Genre()));
        given(bookService.getAll()).willReturn(bookList);

        mvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("listBook"));
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
                .andExpect(view().name("editBook"));
    }

    /*** КОММЕНТАРИИ ***/

    @Test
    @DisplayName("тест контроллера списка комментариев")
    void listCommentTest() throws Exception {
        given(bookService.getById(anyLong())).willReturn(new Book(101L, "Test title", new Author(), new Genre()));

        mvc.perform(get("/listComment").param("bookId","101"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("listComment"));
    }

    @Test
    @DisplayName("тест гет-контроллера добавления комментария")
    void addCommentTest() throws Exception {
        given(bookService.getById(anyLong())).willReturn(new Book(101L, "Test title", new Author(), new Genre()));

        mvc.perform(get("/addComment").param("bookId","101"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("addComment"));
    }

    @Test
    @DisplayName("тест гет-контроллера изменения комментария")
    void editCommentTest() throws Exception {
        Book book = new Book(101L, "Test title", new Author(), new Genre());
        given(bookService.getById(anyLong())).willReturn(book);
        given(commentService.getById(anyLong())).willReturn(new Comment(101L, "Text comment", book));
        mvc.perform(get("/editComment").param("id", "101").param("bookId","101"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("editComment"));
    }
}
