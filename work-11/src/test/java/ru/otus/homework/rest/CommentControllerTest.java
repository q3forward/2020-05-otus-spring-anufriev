package ru.otus.homework.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.dto.CommentDto;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.CommentRepository;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@WebFluxTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CommentRepository commentRepo;
    @MockBean
    private BookRepository bookRepo;

    @Test
    @DisplayName("тест рест контроллера получения комментариев книги")
    public void getCommentsByBookTest() {
        given(commentRepo.getCommentsByBookId(anyString())).willReturn(Flux.fromIterable(Arrays.asList(new Comment("1", "Comment1", new Book()), new Comment("2", "Comment2", new Book()))));

        webTestClient.get().uri("/api/comment/book/100")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].id").isEqualTo("1")
                .jsonPath("$[0].text").isEqualTo("Comment1")
                .jsonPath("$[1].id").isEqualTo("2")
                .jsonPath("$[1].text").isEqualTo("Comment2");
    }

    @Test
    @DisplayName("тест рест контроллера получения комментария по id")
    public void getCommentByIdTest() {
        given(commentRepo.findById(anyString())).willReturn(Mono.just(new Comment("1", "Comment1", new Book())));

        webTestClient.get().uri("/api/comment/100")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.text").isEqualTo("Comment1");
    }

    @Test
    @DisplayName("тест рест контроллера добавления комментария")
    public void addCommentTest() {
        Book book = new Book();
        CommentDto commentDto = new CommentDto("1", "Comment1");
        given(bookRepo.findById(anyString())).willReturn(Mono.just(book));
        given(commentRepo.save(any(Comment.class))).willReturn(Mono.just(new Comment("1", "Comment1", book)));

        webTestClient.post().uri("/api/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(commentDto), CommentDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("тест рест контроллера изменения комментария")
    public void editCommentTest() {
        Book book = new Book();
        CommentDto commentDto = new CommentDto("1", "Comment2");
        given(bookRepo.findById(anyString())).willReturn(Mono.just(book));
        given(commentRepo.findById(anyString())).willReturn(Mono.just(new Comment("1", "Comment1", book)));
        given(commentRepo.save(any(Comment.class))).willReturn(Mono.just(new Comment("1", "Comment2", book)));

        webTestClient.put().uri("/api/comment/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(commentDto), CommentDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("тест рест контроллера удаления комментария")
    public void deleteCommentTest() {
        given(commentRepo.findById(anyString())).willReturn(Mono.just(new Comment("1", "Comment1", new Book())));
        given(commentRepo.delete(any(Comment.class))).willReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/comment/{id}", "1")
                .exchange()
                .expectStatus().isOk();
    }
}
