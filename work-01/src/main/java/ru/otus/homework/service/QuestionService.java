package ru.otus.homework.service;

import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;

import java.util.List;

public interface QuestionService {

    /**
     *
     * @param num Номер вопроса
     * @param questionContent Текст вопроса
     * @param answerList Лист с ответами
     * @return Объект вопроса
     */
    Question createQuestion(Integer num, String questionContent, List<Answer> answerList);

    /**
     *
     * @param rawData строка с данными из CSV, соответствующая одному вопросу
     * @return Объект вопроса, включающий номер вопроса, содержание вопроса, лист с ответами
     */
    Question createQuestion(String rawData);
}
