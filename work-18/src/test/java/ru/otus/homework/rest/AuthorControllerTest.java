package ru.otus.homework.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.controller.ViewController;
import ru.otus.homework.domain.Author;
import ru.otus.homework.service.AuthorService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Test
    @DisplayName("тест рест контроллера списка авторов")
    public void getAuthorsTest() throws Exception {
        given(authorService.findAll()).willReturn(Arrays.asList(new Author(1L, "Author1"), new Author(2L, "Author2")));

        mvc.perform(get("/api/authors"))
            .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]",hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Author1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Author2")));
    }
}
