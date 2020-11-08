package ru.otus.homework.domain.abbvie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Drug")
public class DrugTest {

    @DisplayName("успешно создается через конструктор")
    @Test
    void shouldHaveCorrectConstructor() {

        Drug drug = new Drug("100", "TestPermalink", "TestBrandName",
                "TestTherapyArea", "TestTherapyAreaLabel", "TestType",
                "TestTypeLabel", "TestDescription", new ArrayList());

        assertEquals("100", drug.getId());
        assertEquals("TestPermalink", drug.getPermalink());
        assertEquals("TestBrandName", drug.getBrandName());
        assertEquals("TestTherapyArea", drug.getTherapyArea());
        assertEquals("TestTherapyAreaLabel", drug.getTherapyAreaLabel());
        assertEquals("TestType", drug.getType());
        assertEquals("TestTypeLabel", drug.getTypeLabel());
        assertEquals("TestDescription", drug.getDescription());
    }
}
