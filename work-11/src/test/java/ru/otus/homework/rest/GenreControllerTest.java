package ru.otus.homework.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.GenreRepository;

import java.util.Arrays;
import static org.mockito.BDDMockito.given;

@WebFluxTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GenreRepository genreRepo;

    @Test
    @DisplayName("тест рест контроллера списка жанров")
    public void getGenresTest() {
        given(genreRepo.findAll()).willReturn(Flux.fromIterable(Arrays.asList(new Genre("1", "Genre1"), new Genre("2", "Genre2"))));

        webTestClient.get().uri("/api/genres")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].id").isEqualTo("1")
                .jsonPath("$[0].name").isEqualTo("Genre1")
                .jsonPath("$[1].id").isEqualTo("2")
                .jsonPath("$[1].name").isEqualTo("Genre2");
    }
}
