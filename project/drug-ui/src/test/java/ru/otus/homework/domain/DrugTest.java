package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс Drug")
@ExtendWith(MockitoExtension.class)
public class DrugTest {

    @Mock
    private Company company;

    @DisplayName("успешно создается через конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        given(company.getId()).willReturn(100L);
        given(company.getName()).willReturn("CompanyName");
        Drug drug = new Drug(1L, company, "TestDrug", "TestArea", "TestPhase", "Descr", "TestLink");

        assertEquals(1L, drug.getId());
        assertEquals("TestDrug", drug.getName());
        assertEquals("TestArea", drug.getArea());
        assertEquals("TestPhase", drug.getPhase());
        assertEquals("Descr", drug.getDescription());
        assertEquals("TestLink", drug.getLink());
        assertEquals(100L, drug.getCompany().getId());
        assertEquals("CompanyName", drug.getCompany().getName());
    }

}
