package ru.otus.homework.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ViewController.class)
public class ViewControllerTest {

    @Autowired
    private MockMvc mvc;

    /*** ПРЕПАРАТЫ ***/

    @Test
    @DisplayName("тест контроллера списка препаратов")
    public void listDrugViewTest() throws Exception {
        mvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("listDrug"));
    }

    @Test
    @DisplayName("тест гет-контроллера добавления препаратов")
    void addDrugTest() throws Exception {
        mvc.perform(get("/addDrug"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("addDrug"));
    }

    @Test
    @DisplayName("тест гет-контроллера изменения препарата")
    void editDrugTest() throws Exception {
        mvc.perform(get("/editDrug").param("id", "101"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("editDrug"));
    }

    /*** КОМПАНИИ ***/

    @Test
    @DisplayName("тест контроллера списка компаний")
    void listCompanyTest() throws Exception {
        mvc.perform(get("/listCompany"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("listCompany"));
    }

    @Test
    @DisplayName("тест гет-контроллера добавления компании")
    void addCompanyTest() throws Exception {
        mvc.perform(get("/addCompany"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("addCompany"));
    }

    @Test
    @DisplayName("тест гет-контроллера изменения компании")
    void editCompanyTest() throws Exception {
        mvc.perform(get("/editCompany").param("id", "101"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("editCompany"));
    }
}
