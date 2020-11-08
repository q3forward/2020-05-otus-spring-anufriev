package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestOperations;
import ru.otus.homework.domain.DrugDto;
import ru.otus.homework.domain.bms.Pipeline;
import ru.otus.homework.domain.bms.BmsDrug;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@DisplayName("Тест сервиса работы с препаратами")
@ExtendWith(MockitoExtension.class)
class BmsReceiveServTest {

    @Mock
    private RestOperations rest;
    @InjectMocks
    private BmsReceiveServ service;

    @Test
    @DisplayName("тест преобразования к dto данных bms")
    void getDataTest() {
        ReflectionTestUtils.setField(service, "url", "someoneUrl");
        BmsDrug drug = BmsDrug.builder()
                .compoundname("<p><a href='https://testLink1' title='' class='' target=''  aria-label=''><b>Test Drug1</b></p>\\r\\n")
                .category("bms:therapeutiic-area/solid-tumors")
                .phaseTag("bms:phase/phase-2")
                .researcharea("<p>Area 1</p>\\r\\n")
                .build();
        BmsDrug drug2 = BmsDrug.builder()
                .compoundname("<p><a href='https://testLink2' title='' class='' target=''  aria-label=''><b>Test Drug2</b></p>\\r\\n")
                .category("bms:therapeutiic-area/solid-tumors")
                .phaseTag("bms:phase/phase-3")
                .researcharea("<p>Area 2</p>\\r\\n")
                .build();
        List<BmsDrug> listings = Arrays.asList(drug, drug2);
        Pipeline pipe = new Pipeline();
        pipe.setListings(listings);
        given(rest.getForObject(anyString(), eq(Pipeline.class))).willReturn(pipe);

        List<DrugDto> expectedList = Arrays.asList(
                new DrugDto("Test Drug1","Area 1","2",null,"https://testLink1"),
                new DrugDto("Test Drug2","Area 2","3",null,"https://testLink2"));

        List<DrugDto> actualList = service.getData();
        assertThat(actualList)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(expectedList);
    }
}
