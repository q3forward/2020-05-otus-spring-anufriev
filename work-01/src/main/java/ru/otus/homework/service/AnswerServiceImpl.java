package ru.otus.homework.service;

import ru.otus.homework.dao.AnswerDao;
import ru.otus.homework.domain.Answer;

public class AnswerServiceImpl implements AnswerService {

    private final AnswerDao dao;

    public AnswerServiceImpl(AnswerDao dao) {
        this.dao = dao;
    }

    @Override
    public Answer createAnswer(String answerContent) {
        return dao.createAnswer(answerContent);
    }

}
