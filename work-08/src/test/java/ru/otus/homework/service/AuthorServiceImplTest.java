package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.domain.Author;
import ru.otus.homework.repository.AuthorRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@DisplayName("Тест сервиса работы с авторами")
@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository repo;
    @InjectMocks
    private AuthorServiceImpl service;

    @Test
    @DisplayName("тест поиска по Id")
    void findByIdTest() {
        Optional<Author> author = Optional.of(new Author("1", "Test author"));
        given(repo.findById(anyString())).willReturn(author);

        Optional<Author> actual = service.findById("1");
        assertThat(actual).isNotEmpty().get().isEqualToComparingFieldByField(author.get());
    }

    @Test
    @DisplayName("тест поиска по имени")
    void findByNameTest() {
        Author author2 = new Author("2", "Test author2");
        List<Author> expectedList = Collections.singletonList(author2);
        given(repo.getByName(any())).willReturn(expectedList);

        List<Author> actualList = service.findByName("Test author2");
        assertThat(actualList)
                .isNotNull()
                .isInstanceOf(List.class)
                .hasSize(1)
                .isEqualTo(expectedList);
    }

    @Test
    @DisplayName("тест поиска всех записей")
    void findAllTest() {
        Author author = new Author("1", "Test author");
        Author author2 = new Author("2", "Test author");
        List<Author> expectedList = Arrays.asList(author, author2);
        given(repo.findAll()).willReturn(expectedList);

        Iterable<Author> actualList = service.findAll();
        assertThat(actualList)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(expectedList);
    }

    @Test
    @DisplayName("тест добавления")
    void addTest() {
        Author author = new Author("1", "Test author");
        given(repo.save(any())).willReturn(author);

        Author actual = service.add("Test author");
        assertThat(actual)
                .isNotNull()
                .isEqualToComparingFieldByField(author);
    }
}
