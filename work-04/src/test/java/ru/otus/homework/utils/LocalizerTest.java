package ru.otus.homework.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.homework.config.ApplicationSettings;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("Class Localizer")
@SpringBootTest
public class LocalizerTest {

    private MessageSource messageSource;
    @MockBean
    private ApplicationSettings appSettings;

    private final Locale ruLoc = new Locale("ru");
    private final Locale enLoc = Locale.ENGLISH;

    @DisplayName("test localizing List of strings")
    @Test
    void localizeListStringTest() {
        given(appSettings.getLocale())
                .willReturn(ruLoc);
        List<String> locList = Localizer.localize(Arrays.asList("tst-string","tst-another-string"));
        assertEquals(2, locList.size());
        assertEquals("Тестовая строка", locList.get(0));
        assertEquals("Другая тестовая строка", locList.get(1));

        given(appSettings.getLocale())
                .willReturn(enLoc);
        locList = Localizer.localize(Arrays.asList("tst-string","tst-another-string"));
        assertEquals(2, locList.size());
        assertEquals("Test string", locList.get(0));
        assertEquals("Another test string", locList.get(1));
    }

    @DisplayName("test localizing string")
    @Test
    void localizeStringTest() {
        given(appSettings.getLocale())
                .willReturn(enLoc);
        String str = Localizer.localize("tst-string");
        assertEquals("Test string", str);

        given(appSettings.getLocale())
                .willReturn(ruLoc);
        str = Localizer.localize("tst-string");
        assertEquals("Тестовая строка", str);
    }

    @DisplayName("test localizing string with param")
    @Test
    void localizeStringWithParamTest() {
        given(appSettings.getLocale())
                .willReturn(enLoc);
        String str = Localizer.localize("tst-string-param", new Integer[]{5});
        assertEquals("String with param 5", str);

        given(appSettings.getLocale())
                .willReturn(ruLoc);
        str = Localizer.localize("tst-string-param", new Integer[]{5});
        assertEquals("Строка с параметром 5", str);
    }

}
