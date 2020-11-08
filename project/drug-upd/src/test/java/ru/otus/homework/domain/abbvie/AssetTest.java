package ru.otus.homework.domain.abbvie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Asset")
public class AssetTest {

    @DisplayName("успешно создается через конструктор")
    @Test
    void shouldHaveCorrectConstructor() {
        Asset asset = new Asset("100", "TestIndication", "TestPhase", "TestType", "TestDescr", "TestPhaseLabel");

        assertEquals("100", asset.getId());
        assertEquals("TestIndication", asset.getIndication());
        assertEquals("TestPhase", asset.getPhase());
        assertEquals("TestType", asset.getType());
        assertEquals("TestDescr", asset.getDescription());
        assertEquals("TestPhaseLabel", asset.getPhaseLabel());
    }
}
