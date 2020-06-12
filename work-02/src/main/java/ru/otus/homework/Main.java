package ru.otus.homework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.otus.homework.service.QuestionService;

@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(Main.class);
        QuestionService questionService = appContext.getBean(QuestionService.class);
        questionService.runTest();
    }

}
