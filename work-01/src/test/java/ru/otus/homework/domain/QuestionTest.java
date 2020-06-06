package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Class Question")
public class QuestionTest {

    @DisplayName("constructor success creating")
    @Test
    void shouldHaveCorrectConstructor() {
        List<Answer> answerList = new ArrayList<>();
        answerList.add(new Answer("Answer content"));
        Question question = new Question(1, "Quesion content", answerList);

        assertEquals(1, question.getQuestionNum());
        assertEquals("Quesion content", question.getQuestionContent());
        assertEquals(1, question.getAnswerList().size());
        assertEquals("Answer content", question.getAnswerList().get(0).getAnswerContent());
    }
}
