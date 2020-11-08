package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс DrugDto")
public class DrugDtoTest {

    @DisplayName("успешно создается через конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        DrugDto drug = new DrugDto("TestDrug", "TestArea", "TestPhase", "TestDescr", "TestLink");

        assertEquals("TestDrug", drug.getName());
        assertEquals("TestArea", drug.getArea());
        assertEquals("TestPhase", drug.getPhase());
        assertEquals("TestDescr", drug.getDescription());
        assertEquals("TestLink", drug.getLink());
    }
}
