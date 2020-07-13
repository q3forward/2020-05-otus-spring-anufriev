package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Genre;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayName("Тест сервиса работы с жанрами")
@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    @Mock
    private GenreDao GenreDao;
    @InjectMocks
    private GenreServiceImpl service;

    @Test
    @DisplayName("тест поиска по имени")
    void findByNameTest() {
        Genre expected = new Genre(2L, "Test genre2");
        given(GenreDao.getByName(any())).willReturn(expected);

        Genre actual = service.findByName("Test genre2");
        assertThat(actual)
                .isNotNull()
                .isEqualToComparingFieldByField(expected);
    }

    @Test
    @DisplayName("тест поиска всех записей")
    void findAllTest() {
        Genre genre = new Genre(1L, "Test Genre");
        Genre genre2 = new Genre(2L, "Test Genre");
        List<Genre> expectedList = Arrays.asList(genre, genre2);
        given(GenreDao.getAll()).willReturn(expectedList);

        List<Genre> actualList = service.findAll();
        assertThat(actualList)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(expectedList);
    }

    @Test
    @DisplayName("тест добавления")
    void addTest() {
        Genre genre = new Genre(1L, "Test Genre");
        given(GenreDao.save(any())).willReturn(genre);

        Genre actual = service.add("Test Genre");
        assertThat(actual)
                .isNotNull()
                .isEqualToComparingFieldByField(genre);
    }
}
