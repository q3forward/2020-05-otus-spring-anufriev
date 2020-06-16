package ru.otus.homework.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.homework.config.ApplicationSettings;
import ru.otus.homework.domain.Question;
import ru.otus.homework.service.OutputService;
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
    private static final String RESOURCE_PATH = "test.csv";

    @MockBean
    private ResourceLoader resourceLoader;
    @MockBean
    private ApplicationSettings appSettings;
    private OutputService outputService;

    private QuestionDaoImpl questionDaoImpl;

    @BeforeEach
    void setUp() {
        given(resourceLoader.getData(any()))
                .willReturn(Arrays.asList("1_tst-question_tst-rightAnswer_tst-answer-content"));
        given(appSettings.getCsvName())
                .willReturn(RESOURCE_PATH);
        given(appSettings.getLocale())
                .willReturn(Locale.ENGLISH);
        questionDaoImpl = new QuestionDaoImpl(resourceLoader, outputService, appSettings);
    }

    @DisplayName("test getQuestions En")
    @Test
    void getQuestionsEnTest() {
        List<Question> questions = questionDaoImpl.getQuestions();
        assertThat(questions)
                .isNotNull()
                .isInstanceOf(List.class)
                .hasSize(1);

        Question question = questions.get(0);
        assertAll("",
                () -> assertEquals(1, question.getQuestionNum()),
                () -> assertEquals("Test question", question.getQuestionContent()),
                () -> assertEquals("Test right answer", question.getRightAnswer()),
                () -> assertEquals(1, question.getAnswerList().size()),
                () -> assertEquals("Test answer content", question.getAnswerList().get(0).getAnswerContent())
        );
    }

}
