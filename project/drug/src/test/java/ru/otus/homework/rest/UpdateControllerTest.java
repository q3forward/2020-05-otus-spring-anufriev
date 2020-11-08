package ru.otus.homework.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;
import ru.otus.homework.domain.Company;
import ru.otus.homework.domain.Drug;
import ru.otus.homework.service.UpdateService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UpdateController.class)
public class UpdateControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UpdateService updateService;

    @Test
    @DisplayName("тест рест контроллера загрузки препаратов")
    public void updateDrugsTest() throws Exception {
        Company comp1 = new Company("Company1");
        Company comp2 = new Company("Company2");
        given(updateService.update(anyString())).willReturn(Arrays.asList(
                new Drug(100L,comp1,"Drug1","Area1","1","Desc1","Link1"),
                new Drug(101L,comp2,"Drug2","Area2","2","Desc2","Link2")));

        mvc.perform(get("/api/update/Xcompany"))
            .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]",hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(100)))
                .andExpect(jsonPath("$[0].name", is("Drug1")))
                .andExpect(jsonPath("$[0].area", is("Area1")))
                .andExpect(jsonPath("$[0].phase", is("1")))
                .andExpect(jsonPath("$[0].description", is("Desc1")))
                .andExpect(jsonPath("$[0].link", is("Link1")))
                .andExpect(jsonPath("$[1].id", is(101)))
                .andExpect(jsonPath("$[1].name", is("Drug2")))
                .andExpect(jsonPath("$[1].area", is("Area2")))
                .andExpect(jsonPath("$[1].phase", is("2")))
                .andExpect(jsonPath("$[1].description", is("Desc2")))
                .andExpect(jsonPath("$[1].link", is("Link2")));
    }

    @Test
    @DisplayName("тест рест контроллера успешного обновления препаратов")
    public void refreshDrugsTest() throws Exception {
        doNothing().when(updateService).refreshDrugs(anyString());

        mvc.perform(get("/api/refreshdata/Xcompany"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("тест рест контроллера неуспешного обновления препаратов")
    public void refreshDrugsErrorTest() throws Exception {
        doThrow(new RestClientException("Msg")).when(updateService).refreshDrugs(anyString());

        mvc.perform(get("/api/refreshdata/Xcompany"))
                .andExpect(status().is5xxServerError());
    }
}
