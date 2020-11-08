package ru.otus.homework.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.domain.Company;
import ru.otus.homework.rest.CompanyController;
import ru.otus.homework.service.CompanyService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompanyService companyService;

    @Test
    @DisplayName("тест рест контроллера списка компаний")
    public void findCompanysTest() throws Exception {
        given(companyService.findAll()).willReturn(Arrays.asList(
                new Company(100L,"Company1","comp1","Country",2000,"link1"),
                new Company(101L,"Company2","comp2","Country",2010,"link2")));

        mvc.perform(get("/api/company"))
            .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]",hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(100)))
                .andExpect(jsonPath("$[0].name", is("Company1")))
                .andExpect(jsonPath("$[0].brief", is("comp1")))
                .andExpect(jsonPath("$[0].country", is("Country")))
                .andExpect(jsonPath("$[0].year", is(2000)))
                .andExpect(jsonPath("$[0].link", is("link1")))
                .andExpect(jsonPath("$[1].id", is(101)))
                .andExpect(jsonPath("$[1].name", is("Company2")))
                .andExpect(jsonPath("$[1].brief", is("comp2")))
                .andExpect(jsonPath("$[1].country", is("Country")))
                .andExpect(jsonPath("$[1].year", is(2010)))
                .andExpect(jsonPath("$[1].link", is("link2")));
    }

    @Test
    @DisplayName("тест рест контроллера получения компании по id")
    public void findCompanyByIdTest() throws Exception {
        given(companyService.findById(anyLong())).willReturn(
                new Company(100L,"Company1","comp1","Country",2000,"link1"));

        mvc.perform(get("/api/company/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(100)))
                .andExpect(jsonPath("$.name", is("Company1")))
                .andExpect(jsonPath("$.brief", is("comp1")))
                .andExpect(jsonPath("$.country", is("Country")))
                .andExpect(jsonPath("$.year", is(2000)))
                .andExpect(jsonPath("$.link", is("link1")));
    }

    @Test
    @DisplayName("тест рест контроллера добавления компании")
    public void addCompanyTest() throws Exception {
        given(companyService.findById(anyLong())).willReturn(
                new Company(100L,"Company1","comp1","Country",2000,"link1"));

        mvc.perform(post("/api/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Company1\", \"brief\":\"comp1\", \"country\":\"Country\", \"year\":\"2000\", \"link\":\"link1\" }"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("тест рест контроллера изменения компании")
    public void editCompanyTest() throws Exception {
        mvc.perform(put("/api/company/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Company2\", \"brief\":\"comp2\", \"country\":\"Country2\", \"year\":\"2010\", \"link\":\"link2\" }"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("тест рест контроллера удаления компании")
    public void deleteCompanyTest() throws Exception {
        mvc.perform(delete("/api/company/100"))
                .andExpect(status().isOk());
    }
}
