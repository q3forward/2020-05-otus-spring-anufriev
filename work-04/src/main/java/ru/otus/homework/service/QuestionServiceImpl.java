package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.config.ApplicationSettings;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.utils.exception.IncorrectQuestionException;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;
    private final OutputService outputService;
    private final InputService inputService;
    private final ApplicationSettings appSettings;
    private final Localizer localizer;

    private int countForPass;
    private int rightCounts = 0;

    public QuestionServiceImpl(QuestionDao dao, OutputService outputService, InputService inputService,
                               ApplicationSettings appSettings, Localizer localizer) {
        this.dao = dao;
        this.outputService = outputService;
        this.inputService = inputService;
        this.appSettings = appSettings;
        this.localizer = localizer;
    }

    @Override
    public void runTest() {

        try {
            dao.getQuestions().forEach(question -> {
                outputService.writeOut(question);
                String answer = inputService.writeIn();
                if (answer.equalsIgnoreCase(question.getRightAnswer())) rightCounts++;
            });
            outputService.writeOut(localizer.localize("test-finished"));

        } catch(IncorrectQuestionException e) {
            outputService.writeOut(localizer.localize("error-test"));
        }
    }

    @Override
    public void showResults() {
        outputService.writeOut(localizer.localize(rightCounts >= appSettings.getRightAnswerNeedForPass()
                ? "test-result-success" : "test-result-fail", rightCounts));
    }
}
