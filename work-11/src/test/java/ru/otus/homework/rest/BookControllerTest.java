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
import ru.otus.homework.domain.*;
import ru.otus.homework.domain.Book;
import ru.otus.homework.dto.BookDto;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.GenreRepository;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@WebFluxTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepository bookRepo;
    @MockBean
    private AuthorRepository authorRepo;
    @MockBean
    private GenreRepository genreRepo;

    @Test
    @DisplayName("тест рест контроллера списка книг")
    public void getBooksTest() {
        given(bookRepo.findAll()).willReturn(
                Flux.fromIterable(Arrays.asList(
                        new Book("1", "Book1", new Author(), new Genre()),
                        new Book("2", "Book2", new Author(), new Genre()))));

        webTestClient.get().uri("/api/book")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].id").isEqualTo("1")
                .jsonPath("$[0].title").isEqualTo("Book1")
                .jsonPath("$[1].id").isEqualTo("2")
                .jsonPath("$[1].title").isEqualTo("Book2");
    }

    @Test
    @DisplayName("тест рест контроллера получения книги по id")
    public void getBookByIdTest() {
        given(bookRepo.findById(anyString())).willReturn(Mono.just(new Book("1", "Book1", new Author(), new Genre())));

        webTestClient.get().uri("/api/book/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.title").isEqualTo("Book1");
    }

    @Test
    @DisplayName("тест рест контроллера добавления книги")
    public void addBookTest() {
        given(authorRepo.findAllByName(anyString()))
            .willReturn(Flux.fromIterable(
                    Arrays.asList(new Author("1", "Author1"))));
        given(genreRepo.findByName(anyString()))
                .willReturn(Mono.just(
                        new Genre("1", "Genre1")));
        given(bookRepo.save(any(Book.class))).willReturn(Mono.just(new Book("1", "Book1", new Author(), new Genre())));

        BookDto bookDto = new BookDto("1", "Book2", "authorName", "genreName");
        webTestClient.post().uri("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(bookDto), BookDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("тест рест контроллера изменения книги")
    public void editBookTest() {
        given(authorRepo.findAllByName(anyString()))
                .willReturn(Flux.fromIterable(
                        Arrays.asList(new Author("1", "Author1"))));
        given(genreRepo.findByName(anyString()))
                .willReturn(Mono.just(
                        new Genre("1", "Genre1")));
        given(bookRepo.findById(anyString())).willReturn(Mono.just(new Book("100", "Book1", new Author(), new Genre())));
        given(bookRepo.save(any(Book.class))).willReturn(Mono.just(new Book("100", "Book2", new Author(), new Genre())));

        BookDto bookDto = new BookDto("1", "Book2", "authorName", "genreName");
        webTestClient.put().uri("/api/book/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(bookDto), BookDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("тест рест контроллера удаления книги")
    public void deleteBookTest() {
        given(bookRepo.findById(anyString())).willReturn(Mono.just(new Book("1", "Title1", new Author(), new Genre())));
        given(bookRepo.delete(any(Book.class))).willReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/book/{id}", "100")
                .exchange()
                .expectStatus().isOk();
    }
}
