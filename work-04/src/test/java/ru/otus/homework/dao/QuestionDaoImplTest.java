package ru.otus.homework.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.homework.config.ApplicationSettings;
import ru.otus.homework.domain.Question;
import ru.otus.homework.utils.ResourceLoader;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("QuestionDaoImpl Class")
@SpringBootTest
public class QuestionDaoImplTest {
    private static final String RESOURCE_NAME = "test";
    private static final String RESOURCE_EXT = ".csv";

    @MockBean
    private ResourceLoader resourceLoader;

    @MockBean
    private ApplicationSettings appSettings;

    @Autowired
    private QuestionDaoImpl questionDaoImpl;

    @BeforeEach
    void setUp() {
        given(resourceLoader.getData(any()))
                .willReturn(Arrays.asList("1_tst-question_tst-rightAnswer_tst-answer-content"));
        given(appSettings.getCsvName())
                .willReturn(RESOURCE_NAME);
        given(appSettings.getCsvExt())
                .willReturn(RESOURCE_EXT);
    }

    @DisplayName("test getQuestions")
    @Test
    void getQuestionsEnTest() {
        given(appSettings.getLocale())
                .willReturn(Locale.ENGLISH);

        List<Question> questions = questionDaoImpl.getQuestions();
        assertThat(questions)
                .isNotNull()
                .isInstanceOf(List.class)
                .hasSize(1);

        Question question = questions.get(0);
        assertAll("",
                () -> assertEquals(1, question.getQuestionNum()),
                () -> assertEquals("tst-question", question.getQuestionContent()),
                () -> assertEquals("tst-rightAnswer", question.getRightAnswer()),
                () -> assertEquals(1, question.getAnswerList().size()),
                () -> assertEquals("tst-answer-content", question.getAnswerList().get(0).getAnswerContent())
        );
    }

}