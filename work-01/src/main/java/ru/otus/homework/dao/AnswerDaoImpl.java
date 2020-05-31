package ru.otus.homework.dao;

import ru.otus.homework.domain.Answer;

public class AnswerDaoImpl implements AnswerDao {

    @Override
    public Answer createAnswer(String answerContent) {
        return new Answer(answerContent);
    }
}
