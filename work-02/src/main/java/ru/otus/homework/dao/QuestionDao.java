package ru.otus.homework.dao;

import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> getQuestions();
}
