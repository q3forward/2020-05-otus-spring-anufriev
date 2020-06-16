package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.homework.config.ApplicationSettings;
import ru.otus.homework.service.QuestionService;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationSettings.class)
public class Main {

    public static void main(String[] args) {
        var context = SpringApplication.run(Main.class, args);
        QuestionService questionService = context.getBean(QuestionService.class);
        questionService.runTest();
    }

}
