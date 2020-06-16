package ru.otus.homework.dao;

import org.springframework.stereotype.Repository;
import ru.otus.homework.config.ApplicationSettings;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.service.OutputService;
import ru.otus.homework.utils.Localizer;
import ru.otus.homework.utils.ResourceLoader;
import ru.otus.homework.utils.exception.IncorrectQuestionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    private final ResourceLoader resourceLoader;
    private final OutputService outputService;
    private final ApplicationSettings appSettings;

    public QuestionDaoImpl(ResourceLoader resourceLoader, OutputService outputService,
                           ApplicationSettings appSettings) {
        this.resourceLoader = resourceLoader;
        this.outputService = outputService;
        this.appSettings = appSettings;
    }

    @Override
    public List<Question> getQuestions() {
        List<String> rawData = resourceLoader.getData(appSettings.getCsvName());
        List<Question> questions = new ArrayList<>();
        for (String record: rawData) {
            String[] questionData = record.split("_");
            try {
                List<String> answers = Localizer.localize(Arrays.asList(Arrays.copyOfRange(questionData, 3, questionData.length)));
                questions.add(new Question(Integer.valueOf(questionData[0]), Localizer.localize(questionData[1]), Localizer.localize(questionData[2]), answers.stream().map(Answer::new).collect(Collectors.toList())));
            } catch (IllegalArgumentException e) {
                outputService.writeOut(Localizer.localize("error-test"));
                throw new IncorrectQuestionException(e);
            }
        }
        return questions;
    }

}
