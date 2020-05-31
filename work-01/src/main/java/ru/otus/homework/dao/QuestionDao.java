package ru.otus.homework.dao;

import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;

import java.util.List;

public interface QuestionDao {

    Question createQuestion(Integer num, String questionContent, List<Answer> answerList);
}
