package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.QuestionDao;

@Service
@PropertySource("classpath:application.properties")
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;
    private final OutputService outputService;
    private final InputService inputService;

    @Value("${rightAnswer.needForPass}")
    private int countForPass;
    private int rightCounts = 0;

    public QuestionServiceImpl(QuestionDao dao, OutputService outputService, InputService inputService) {
        this.dao = dao;
        this.outputService = outputService;
        this.inputService = inputService;
    }

    @Override
    public void printQuestions() {
        outputService.writeOut("What is your name?");
        String studentName = inputService.writeIn();

        dao.getQuestions().forEach(question -> {
            outputService.writeOut(question);
            String answer = inputService.writeIn();
            if (answer.equalsIgnoreCase(question.getRightAnswer())) rightCounts++;
        });

        outputService.writeOut(rightCounts>=countForPass
                ? String.format("%d right answers! Test successfully passed!", rightCounts)
                : String.format("%d right answers. Test failure.", rightCounts));
    }
}
