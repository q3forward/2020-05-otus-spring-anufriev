package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.domain.Author;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayName("Тест сервиса работы с авторами")
@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorDao authorDao;
    @InjectMocks
    private AuthorServiceImpl service;

    @Test
    @DisplayName("тест поиска по Id")
    void findByIdTest() {
        Optional<Author> author = Optional.of(new Author(1L, "Test author"));
        given(authorDao.getById(anyLong())).willReturn(author);

        Optional<Author> actual = service.findById(1L);
        assertThat(actual).isNotNull();
        assertThat(actual.get()).isEqualToComparingFieldByField(author.get());
    }

    @Test
    @DisplayName("тест поиска по имени")
    void findByNameTest() {
        Author author2 = new Author(2L, "Test author2");
        List<Author> expectedList = Collections.singletonList(author2);
        given(authorDao.getByName(any())).willReturn(expectedList);

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
        Author author = new Author(1L, "Test author");
        Author author2 = new Author(2L, "Test author");
        List<Author> expectedList = Arrays.asList(author, author2);
        given(authorDao.getAll()).willReturn(expectedList);

        List<Author> actualList = service.findAll();
        assertThat(actualList)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(expectedList);
    }

    @Test
    @DisplayName("тест добавления")
    void addTest() {
        Author author = new Author(1L, "Test author");
        given(authorDao.save(any())).willReturn(author);

        Author actual = service.add("Test author");
        assertThat(actual)
                .isNotNull()
                .isEqualToComparingFieldByField(author);
    }
}
