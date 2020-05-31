package ru.otus.homework.dao;

import ru.otus.homework.domain.Answer;

public interface AnswerDao {

    Answer createAnswer(String answerContent);
}
