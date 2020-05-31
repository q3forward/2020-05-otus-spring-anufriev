package ru.otus.homework.utils;

import ru.otus.homework.domain.Question;
import ru.otus.homework.service.QuestionService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ResourceLoader {
    private String resourcePath;
    private QuestionService questionService;

    public ResourceLoader(String resourcePath, QuestionService questionService) {
        this.resourcePath = resourcePath;
        this.questionService = questionService;
    }

    /**
     * Получение данных из CSV-файла. Читает файл по <b>resourcePath</b> и преобразует данные в объекты
     * @return Лист вопросов
     */
    public List<Question> getData() {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
        List<Question> questions = new ArrayList<>();
        try(Scanner scanner = new Scanner(inputStream)){
            while (scanner.hasNextLine()) {
                questions.add(questionService.createQuestion(scanner.nextLine()));
            }
        }
        return questions;
    }

}
