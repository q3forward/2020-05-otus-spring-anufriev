package ru.otus.homework.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.domain.Company;
import ru.otus.homework.domain.Drug;
import ru.otus.homework.service.DrugService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DrugController.class)
public class DrugControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DrugService drugService;

    @Test
    @DisplayName("тест рест контроллера списка препаратов")
    public void findDrugsTest() throws Exception {
        Company comp1 = new Company("Company1");
        Company comp2 = new Company("Company2");
        given(drugService.findAll()).willReturn(Arrays.asList(
                new Drug(100L,comp1,"Drug1","Area1","1","Desc1","Link1"),
                new Drug(101L,comp2,"Drug2","Area2","2","Desc2","Link2")));

        mvc.perform(get("/api/drug"))
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
    @DisplayName("тест рест контроллера получения препарата по id")
    public void findDrugByIdTest() throws Exception {
        Company comp1 = new Company("Company1");
        given(drugService.findById(anyLong())).willReturn(
                new Drug(100L,comp1,"Drug1","Area1","1","Desc1","Link1"));

        mvc.perform(get("/api/drug/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(100)))
                .andExpect(jsonPath("$.name", is("Drug1")))
                .andExpect(jsonPath("$.area", is("Area1")))
                .andExpect(jsonPath("$.phase", is("1")))
                .andExpect(jsonPath("$.description", is("Desc1")))
                .andExpect(jsonPath("$.link", is("Link1")));
    }

    @Test
    @DisplayName("тест рест контроллера добавления препарата")
    public void addDrugTest() throws Exception {
        Company comp1 = new Company("Company1");
        given(drugService.findById(anyLong())).willReturn(
                new Drug(100L,comp1,"Drug1","Area1","1","Desc1","Link1"));

        mvc.perform(post("/api/drug")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Drug1\", \"area\":\"Area1\", \"phase\":\"1\", \"description\":\"Desc1\", \"link\":\"Link1\" }"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("тест рест контроллера изменения препарата")
    public void editDrugTest() throws Exception {
        mvc.perform(put("/api/drug/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Drug2\", \"area\":\"Area2\", \"phase\":\"2\", \"description\":\"Desc2\", \"link\":\"Link2\" }"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("тест рест контроллера удаления препарата")
    public void deleteDrugTest() throws Exception {
        mvc.perform(delete("/api/drug/100"))
                .andExpect(status().isOk());
    }
}
