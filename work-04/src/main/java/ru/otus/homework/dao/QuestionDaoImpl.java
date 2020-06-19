package ru.otus.homework.dao;

import org.springframework.stereotype.Repository;
import ru.otus.homework.config.ApplicationSettings;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.utils.ResourceLoader;
import ru.otus.homework.utils.exception.IncorrectQuestionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    private final ResourceLoader resourceLoader;
    private final ApplicationSettings appSettings;

    public QuestionDaoImpl(ResourceLoader resourceLoader, ApplicationSettings appSettings) throws IncorrectQuestionException{
        this.resourceLoader = resourceLoader;
        this.appSettings = appSettings;
    }

    @Override
    public List<Question> getQuestions() {
        List<String> rawData = resourceLoader.getData(appSettings.getCsvName()+"_"+appSettings.getLocale()+appSettings.getCsvExt());
        List<Question> questions = new ArrayList<>();
        for (String record: rawData) {
            String[] questionData = record.split("_");
            try {
                List<String> answers = Arrays.asList(Arrays.copyOfRange(questionData, 3, questionData.length));
                questions.add(new Question(Integer.valueOf(questionData[0]), questionData[1], questionData[2], answers.stream().map(Answer::new).collect(Collectors.toList())));
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                throw new IncorrectQuestionException(e);
            }
        }
        return questions;
    }

}
