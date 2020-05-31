package ru.otus.homework.service;

import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;
    private AnswerService answerService;

    public QuestionServiceImpl(QuestionDao dao, AnswerService answerService) {
        this.dao = dao;
        this.answerService = answerService;
    }

    @Override
    public Question createQuestion(Integer num, String questionContent, List<Answer> answerList) {
        return dao.createQuestion(num, questionContent, answerList);
    }

    @Override
    public Question createQuestion(String record) {
        String[] questionData = record.split("_");
        List<String> answers = Arrays.asList(Arrays.copyOfRange(questionData,2, questionData.length));
        return createQuestion(Integer.valueOf(questionData[0]), questionData[1], answers.stream().map(answerStr -> answerService.createAnswer(answerStr)).collect(Collectors.toList()));
    }
}
