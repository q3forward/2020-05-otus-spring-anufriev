package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.domain.Company;
import ru.otus.homework.repository.CompanyRepository;
import ru.otus.homework.utils.exception.CompanyNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Тест сервиса работы с компаниями")
@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository repo;
    @InjectMocks
    private CompanyServiceImpl service;

    @Test
    @DisplayName("тест поиска всех записей")
    void findAllTest() {
        Company company = new Company(100L,"Company1","comp1","Country",2000,"link1");
        Company company2 = new Company(101L,"Company2","comp2","Country",2010,"link2");
        List<Company> expectedList = Arrays.asList(company, company2);
        given(repo.findAll()).willReturn(expectedList);

        Iterable<Company> actualList = service.findAll();
        assertThat(actualList)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(expectedList);
    }

    @Test
    @DisplayName("тест поиска по Id")
    void findByIdTest() {
        Optional<Company> company = Optional.of(new Company(100L,"Company1","comp1","Country",2000,"link1"));
        given(repo.findById(anyLong())).willReturn(company);

        Company actual = service.findById(100L);
        assertThat(actual).isNotNull().isEqualToComparingFieldByField(company.get());
    }

    @Test
    @DisplayName("тест добавления")
    void addTest() {
        Company company = new Company(100L,"Company1","comp1","Country",2000,"link1");
        given(repo.save(any())).willReturn(company);

        Company actual = service.add(company);
        assertThat(actual)
                .isNotNull()
                .isEqualToComparingFieldByField(company);
    }

    @Test
    @DisplayName("тест изменения")
    void updateTest() throws CompanyNotFoundException {
        Long id = 100L;
        Optional<Company> oldСompany = Optional.of(new Company(100L,"Company1","comp1","Country",2000,"link1"));
        given(repo.findById(anyLong())).willReturn(oldСompany);
        Company newCompany = new Company(100L, "Company2","comp2","Country2",2010,"link2");
        service.update(100L, newCompany);

        verify(repo, times(1)).findById(id);
        verify(repo, times(1)).save(any());
    }

    @Test
    @DisplayName("тест успешного удаления")
    void deleteTest() throws CompanyNotFoundException {
        Long id = 1L;
        service.delete(id);

        verify(repo, times(1)).deleteById(id);
    }
}
