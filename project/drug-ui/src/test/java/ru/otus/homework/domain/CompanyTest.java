package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Company")
@ExtendWith(MockitoExtension.class)
public class CompanyTest {

    @DisplayName("успешно создается через конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        Company company = new Company(1L,"Company name", "Company brief", "Country", 2005, "http://test.com");

        assertEquals(1L, company.getId());
        assertEquals("Company name", company.getName());
        assertEquals("Company brief", company.getBrief());
        assertEquals("Country", company.getCountry());
        assertEquals(2005, company.getYear());
        assertEquals("http://test.com", company.getLink());
    }
}
