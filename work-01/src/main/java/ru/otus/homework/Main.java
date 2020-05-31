package ru.otus.homework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework.domain.Question;
import ru.otus.homework.service.QuestionService;
import ru.otus.homework.utils.ResourceLoader;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("/spring-context.xml");
        ResourceLoader resourceLoader = (ResourceLoader) appContext.getBean(ResourceLoader.class);
        List<Question> questionList = resourceLoader.getData();

        questionList.forEach(System.out::println);
    }

}
