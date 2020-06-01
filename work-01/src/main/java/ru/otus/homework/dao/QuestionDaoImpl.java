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

    public QuestionDaoImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Question> getQuestions() {
        List<String> rawData = resourceLoader.getData();
        List<Question> questions = new ArrayList<>();
        for (String record: rawData) {
            String[] questionData = record.split("_");
            List<String> answers = Arrays.asList(Arrays.copyOfRange(questionData,2, questionData.length));
            questions.add(new Question(Integer.valueOf(questionData[0]), questionData[1], answers.stream().map(Answer::new).collect(Collectors.toList())));
        }
        return questions;
    }
}
