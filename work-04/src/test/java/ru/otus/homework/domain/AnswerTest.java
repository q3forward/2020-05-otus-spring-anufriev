package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Class Answer")
public class AnswerTest {

    @DisplayName("constructor success creating")
    @Test
    void shouldHaveCorrectConstructor() {
        Answer answer = new Answer("Answer content");

        assertEquals("Answer content", answer.getAnswerContent());
    }
}
