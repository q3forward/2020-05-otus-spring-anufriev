package ru.otus.homework.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;
    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("тест контроллера списка комментариев")
    void listCommentTest() throws Exception {
        given(bookService.getById(anyLong())).willReturn(new Book(101L, "Test title", new Author(), new Genre()));

        mvc.perform(get("/listComment").param("id","101"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("listComment"))
                .andExpect(content().string(containsString("Test title")));
    }

    @Test
    @DisplayName("тест контроллера добавления комментария")
    void addCommentTest() throws Exception {
        given(bookService.getById(anyLong())).willReturn(new Book(101L, "Test title", new Author(), new Genre()));

        mvc.perform(get("/addComment").param("bookId","101"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("addComment"))
                .andExpect(content().string(containsString("101")));
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
                .andExpect(view().name("editComment"))
                .andExpect(content().string(containsString("101")))
                .andExpect(content().string(containsString("Text comment")));
    }

    @Test
    @DisplayName("тест пост-контроллера добавления комментария")
    void postAddCommentTest() throws Exception {
        mvc.perform(post("/addComment").param("bookId","101").param("text", "Comment text"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/listComment?id=101"));
    }

    @Test
    @DisplayName("тест пост-контроллера изменения комментария")
    void postEditCommentTest() throws Exception {
        mvc.perform(post("/editComment").param("id", "101").param("bookId","101").param("text", "Comment text"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/listComment?id=101"));
    }

    @Test
    @DisplayName("тест пост-контроллера удаления комментария")
    void deleteCommentTest() throws Exception {
        Book book = new Book(101L, "Test title", new Author(), new Genre());
        given(commentService.getById(anyLong())).willReturn(new Comment(101L, "Text comment", book));

        mvc.perform(post("/deleteComment").param("id", "101"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/listComment?id=101"));
    }
}
