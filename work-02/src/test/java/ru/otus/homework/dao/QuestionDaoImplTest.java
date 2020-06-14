package ru.otus.homework.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.domain.Question;
import ru.otus.homework.service.OutputConsoleService;
import ru.otus.homework.service.OutputService;
import ru.otus.homework.utils.ResourceLoader;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("QuestionDaoImpl Class")
@ExtendWith(MockitoExtension.class)
public class QuestionDaoImplTest {

    @Mock
    private ResourceLoader resourceLoader;
    private static final String RESOURCE_PATH = "test.csv";
    private OutputService outputService;

    private QuestionDaoImpl questionDaoImpl;

    @BeforeEach
    void setUp() {
        given(resourceLoader.getData(any()))
                .willReturn(Arrays.asList("1_Question content_Right answer_Answer content"));
        questionDaoImpl = new QuestionDaoImpl(resourceLoader, RESOURCE_PATH, outputService);
    }

    @DisplayName("test getQuestions")
    @Test
    void getQuestionsTest() {
        List<Question> questions = questionDaoImpl.getQuestions();
        assertThat(questions)
                .isNotNull()
                .isInstanceOf(List.class)
                .hasSize(1);

        Question question = questions.get(0);
        assertAll("",
                () -> assertEquals(1, question.getQuestionNum()),
                () -> assertEquals("Question content", question.getQuestionContent()),
                () -> assertEquals("Right answer", question.getRightAnswer()),
                () -> assertEquals(1, question.getAnswerList().size()),
                () -> assertEquals("Answer content", question.getAnswerList().get(0).getAnswerContent())
        );
    }
}
