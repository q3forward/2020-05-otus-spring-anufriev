package ru.otus.homework.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.utils.ResourceLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@PropertySource("classpath:application.properties")
public class QuestionDaoImpl implements QuestionDao {

    private final String resourcePath;
    private final ResourceLoader resourceLoader;

    public QuestionDaoImpl(ResourceLoader resourceLoader, @Value("${csv.name}") String resourcePath) {
        this.resourceLoader = resourceLoader;
        this.resourcePath = resourcePath;
    }

    @Override
    public List<Question> getQuestions() {
        List<String> rawData = resourceLoader.getData(resourcePath);
        List<Question> questions = new ArrayList<>();
        for (String record: rawData) {
            String[] questionData = record.split("_");
            try {
                List<String> answers = Arrays.asList(Arrays.copyOfRange(questionData, 3, questionData.length));
                questions.add(new Question(Integer.valueOf(questionData[0]), questionData[1], questionData[2], answers.stream().map(Answer::new).collect(Collectors.toList())));
            } catch (IllegalArgumentException e) {
                // Nothing to do, skip question
            }
        }
        return questions;
    }
}
