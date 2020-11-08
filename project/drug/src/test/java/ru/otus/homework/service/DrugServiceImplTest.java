package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import ru.otus.homework.domain.Company;
import ru.otus.homework.domain.Drug;
import ru.otus.homework.repository.DrugRepository;
import ru.otus.homework.utils.exception.DrugNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Тест сервиса работы с препаратами")
@ExtendWith(MockitoExtension.class)
class DrugServiceImplTest {

    @Mock
    private DrugRepository repo;
    @InjectMocks
    private DrugServiceImpl service;

    @Test
    @DisplayName("тест поиска всех записей")
    void findAllTest() {
        Company comp1 = new Company("Company1");
        Drug drug = new Drug(100L,comp1,"Drug1","Area1","1","Desc1","Link1");
        Drug drug2 = new Drug(101L,comp1,"Drug2","Area2","2","Desc2","Link2");
        List<Drug> expectedList = Arrays.asList(drug, drug2);
        given(repo.findAll(Sort.by(Sort.Direction.DESC, "phase"))).willReturn(expectedList);

        Iterable<Drug> actualList = service.findAll();
        assertThat(actualList)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(expectedList);
    }

    @Test
    @DisplayName("тест поиска по Id")
    void findByIdTest() {
        Company comp1 = new Company("Company1");
        Optional<Drug> drug = Optional.of(new Drug(100L,comp1,"Drug1","Area1","1","Desc1","Link1"));
        given(repo.findById(anyLong())).willReturn(drug);

        Drug actual = service.findById(100L);
        assertThat(actual).isNotNull().isEqualToComparingFieldByField(drug.get());
    }

    @Test
    @DisplayName("тест добавления")
    void addTest() {
        Company comp1 = new Company("Company1");
        Drug drug = new Drug(100L,comp1,"Drug1","Area1","1","Desc1","Link1");
        given(repo.save(any())).willReturn(drug);

        Drug actual = service.add(drug);
        assertThat(actual)
                .isNotNull()
                .isEqualToComparingFieldByField(drug);
    }

    @Test
    @DisplayName("тест изменения")
    void updateTest() throws DrugNotFoundException {
        Long id = 100L;
        Company comp1 = new Company("Company1");
        Optional<Drug> oldСompany = Optional.of(new Drug(100L,comp1,"Drug1","Area1","1","Desc1","Link1"));
        given(repo.findById(anyLong())).willReturn(oldСompany);
        Drug newDrug = new Drug(100L,comp1,"Drug2","Area2","2","Desc2","Link2");
        service.update(100L, newDrug);

        verify(repo, times(1)).findById(id);
        verify(repo, times(1)).save(any());
    }

    @Test
    @DisplayName("тест успешного удаления")
    void deleteTest() throws DrugNotFoundException {
        Long id = 1L;
        service.delete(id);

        verify(repo, times(1)).deleteById(id);
    }
}
