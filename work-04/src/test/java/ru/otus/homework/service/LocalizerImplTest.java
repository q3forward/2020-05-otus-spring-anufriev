package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.otus.homework.config.ApplicationSettings;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@DisplayName("Class LocalizerImpl")
@SpringBootTest
public class LocalizerImplTest {

    @Configuration
    @Import({LocalizerImpl.class, MessageSourceAutoConfiguration.class})
    class TestContextConfig {
    }

    private final Locale ruLoc = new Locale("ru");
    private final Locale enLoc = Locale.ENGLISH;

    @MockBean
    private ApplicationSettings appSettings;

    @MockBean
    private MessageSource messageSource;

    @Autowired
    Localizer localizer;

    @DisplayName("test localizing List of strings")
    @Test
    void localizeListStringTest() {
        given(appSettings.getLocale())
                .willReturn(ruLoc);
        given(messageSource.getMessage(any(), any(), eq(ruLoc)))
                .willReturn("Тестовая строка").willReturn("Другая тестовая строка");

        List<String> locList = localizer.localize(Arrays.asList("tst-string","tst-another-string"));
        assertEquals(2, locList.size());
        assertEquals("Тестовая строка", locList.get(0));
        assertEquals("Другая тестовая строка", locList.get(1));

        given(appSettings.getLocale())
                .willReturn(enLoc);
        given(messageSource.getMessage(any(), any(), eq(enLoc)))
                .willReturn("Test string").willReturn("Another test string");
        locList = localizer.localize(Arrays.asList("tst-string","tst-another-string"));
        assertEquals(2, locList.size());
        assertEquals("Test string", locList.get(0));
        assertEquals("Another test string", locList.get(1));
    }

    @DisplayName("test localizing string")
    @Test
    void localizeStringTest() {
        given(appSettings.getLocale())
                .willReturn(enLoc);
        given(messageSource.getMessage(any(), any(), eq(enLoc)))
                .willReturn("Test string");

        String str = localizer.localize("tst-string");
        assertEquals("Test string", str);

        given(appSettings.getLocale())
                .willReturn(ruLoc);
        given(messageSource.getMessage(any(), any(), eq(ruLoc)))
                .willReturn("Тестовая строка");
        str = localizer.localize("tst-string");
        assertEquals("Тестовая строка", str);
    }

    @DisplayName("test localizing string with param")
    @Test
    void localizeStringWithParamTest() {
        given(appSettings.getLocale())
                .willReturn(enLoc);
        given(messageSource.getMessage(any(), eq(new Integer[]{5}), eq(enLoc)))
                .willReturn("String with param 5");

        String str = localizer.localize("tst-string-param", 5);
        assertEquals("String with param 5", str);

        given(appSettings.getLocale())
                .willReturn(ruLoc);
        given(messageSource.getMessage(any(), eq(new Integer[]{5}), eq(ruLoc)))
                .willReturn("Строка с параметром 5");
        str = localizer.localize("tst-string-param", 5);
        assertEquals("Строка с параметром 5", str);
    }

}
