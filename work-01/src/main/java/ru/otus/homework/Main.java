package ru.otus.homework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework.service.QuestionService;

public class Main {

    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService questionService = appContext.getBean(QuestionService.class);
        questionService.printQuestions();
    }

}
