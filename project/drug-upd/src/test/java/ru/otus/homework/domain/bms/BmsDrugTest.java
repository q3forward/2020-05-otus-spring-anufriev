package ru.otus.homework.domain.bms;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.otus.homework.domain.abbvie.Asset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс BmsDrug")
public class BmsDrugTest {

    @Mock
    private Asset asset;

    @DisplayName("успешно создается через конструктор")
    @Test
    void shouldHaveCorrectConstructor() {

        BmsDrug drug = new BmsDrug("TestCompoundName", "TestPhaseTag", "TestResearchArea",
                "TestCategory", "TestSubCategory", true);

        assertEquals("TestCompoundName", drug.getCompoundname());
        assertEquals("TestPhaseTag", drug.getPhaseTag());
        assertEquals("TestResearchArea", drug.getResearcharea());
        assertEquals("TestCategory", drug.getCategory());
        assertEquals("TestSubCategory", drug.getSubcategory());
        assertEquals(true, drug.getRepeat());
    }
}
