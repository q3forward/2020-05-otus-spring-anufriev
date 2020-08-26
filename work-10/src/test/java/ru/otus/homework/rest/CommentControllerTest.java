package ru.otus.homework.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;
    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("тест рест контроллера получения комментариев книги")
    public void getCommentsByBookTest() throws Exception {
        given(commentService.getBookComments(anyLong())).willReturn(Arrays.asList(new Comment(1L, "Comment1", new Book()), new Comment(2L, "Comment2", new Book())));

        mvc.perform(get("/api/comments/book/100"))
            .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]",hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].text", is("Comment1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].text", is("Comment2")));
    }

    @Test
    @DisplayName("тест рест контроллера получения комментария по id")
    public void getCommentByIdTest() throws Exception {
        given(commentService.getById(anyLong())).willReturn(new Comment(1L, "Comment1", new Book()));

        mvc.perform(get("/api/comment/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.text", is("Comment1")));
    }

    @Test
    @DisplayName("тест рест контроллера добавления комментария")
    public void addCommentTest() throws Exception {
        given(commentService.getById(anyLong())).willReturn(new Comment(1L, "Comment1", new Book()));

        mvc.perform(post("/api/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"text\": \"CommentText\", \"bookId\":\"2\"}"))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("тест рест контроллера изменения комментария")
    public void editCommentTest() throws Exception {

        mvc.perform(put("/api/comment/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"text\": \"CommentText\", \"bookId\":\"2\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("тест рест контроллера удаления комментария")
    public void deleteCommentTest() throws Exception {
        mvc.perform(delete("/api/comment/100"))
                .andExpect(status().isOk());
    }
}
