package ru.otus.homework.dao;

import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;

import java.util.List;

public class QuestionDaoImpl implements QuestionDao {

    @Override
    public Question createQuestion(Integer num, String questionContent, List<Answer> answerList) {
        return new Question(num, questionContent, answerList);
    }
}
