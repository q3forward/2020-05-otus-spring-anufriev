package ru.otus.homework.dao;

import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.utils.ResourceLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionDaoImpl implements QuestionDao {

    private final ResourceLoader resourceLoader;
    private final String resourcePath;

    public QuestionDaoImpl(ResourceLoader resourceLoader, String resourcePath) {
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
                List<String> answers = Arrays.asList(Arrays.copyOfRange(questionData, 2, questionData.length));
                questions.add(new Question(Integer.valueOf(questionData[0]), questionData[1], answers.stream().map(Answer::new).collect(Collectors.toList())));
            } catch (IllegalArgumentException e) {
                // Nothing to do, skip question
            }
        }
        return questions;
    }
}
