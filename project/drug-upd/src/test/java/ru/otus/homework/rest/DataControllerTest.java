package ru.otus.homework.rest;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.domain.DrugDto;
import ru.otus.homework.service.AbbvieReceiveServ;
import ru.otus.homework.service.BmsReceiveServ;
import ru.otus.homework.service.IReceiveService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DataController.class)
public class DataControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IReceiveService receiveServ;

    @BeforeEach
    void setUp() {
        given(receiveServ.getData()).willReturn(Arrays.asList(
                new DrugDto("Drug1","Area1","1","Desc1","Link1"),
                new DrugDto("Drug2","Area2","2","Desc2","Link2")));
    }

    @Test
    @DisplayName("тест рест контроллера получения данных препаратов")
    public void getDataFromUrlExistTest() throws Exception {

        mvc.perform(get("/api/update").param("company", "abbvie"))
            .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]",hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Drug1")))
                .andExpect(jsonPath("$[0].area", is("Area1")))
                .andExpect(jsonPath("$[0].phase", is("1")))
                .andExpect(jsonPath("$[0].description", is("Desc1")))
                .andExpect(jsonPath("$[0].link", is("Link1")))
                .andExpect(jsonPath("$[1].name", is("Drug2")))
                .andExpect(jsonPath("$[1].area", is("Area2")))
                .andExpect(jsonPath("$[1].phase", is("2")))
                .andExpect(jsonPath("$[1].description", is("Desc2")))
                .andExpect(jsonPath("$[1].link", is("Link2")));
    }

    @Test
    @DisplayName("тест рест контроллера получения данных неизвестной компании")
    public void getDataFromUrlNotExistTest() throws Exception {

        mvc.perform(get("/api/update").param("company", "XXX"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
