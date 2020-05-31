package ru.otus.homework.service;

import ru.otus.homework.domain.Answer;

public interface AnswerService {

    /**
     *
     * @param answerContent Содержание ответа
     * @return Объект ответа
     */
    Answer createAnswer(String answerContent);
}
