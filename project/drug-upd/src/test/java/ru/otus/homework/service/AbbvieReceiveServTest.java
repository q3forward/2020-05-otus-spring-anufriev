package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestOperations;
import ru.otus.homework.domain.DrugDto;
import ru.otus.homework.domain.abbvie.Asset;
import ru.otus.homework.domain.abbvie.Drug;
import ru.otus.homework.domain.abbvie.Pipeline;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@DisplayName("Тест сервиса работы с препаратами")
@ExtendWith(MockitoExtension.class)
class AbbvieReceiveServTest {

    @Mock
    private RestOperations rest;
    @InjectMocks
    private AbbvieReceiveServ service;

    @Test
    @DisplayName("тест преобразования к dto данных abbvie")
    void getDataTest() {
        ReflectionTestUtils.setField(service, "url", "someoneUrl");
        Asset asset = Asset.builder().id("id1").phase("Phase 1").build();
        Drug drug = Drug.builder().brandName("brand1").therapyArea("area1").description("desc1").permalink("link1").assets(Arrays.asList(asset)).build();
        Drug drug2 = Drug.builder().brandName("brand2").therapyArea("area2").description("desc2").permalink("link2").assets(Arrays.asList(asset)).build();
        Pipeline pipe = new Pipeline(Arrays.asList(drug, drug2));
        given(rest.getForObject(anyString(), eq(Pipeline.class))).willReturn(pipe);

        List<DrugDto> expectedList = Arrays.asList(
                new DrugDto("brand1","area1","1","desc1","link1"),
                new DrugDto("brand2","area2","1","desc2","link2"));

        List<DrugDto> actualList = service.getData();
        assertThat(actualList)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(expectedList);
    }
}
