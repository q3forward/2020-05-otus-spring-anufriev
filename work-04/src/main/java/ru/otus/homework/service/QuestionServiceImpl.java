package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.config.ApplicationSettings;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.utils.Localizer;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;
    private final OutputService outputService;
    private final InputService inputService;
    private final ApplicationSettings appSettings;

    private int countForPass;
    private int rightCounts = 0;

    public QuestionServiceImpl(QuestionDao dao, OutputService outputService, InputService inputService,
                               ApplicationSettings appSettings) {
        this.dao = dao;
        this.outputService = outputService;
        this.inputService = inputService;
        this.appSettings = appSettings;
    }

    @Override
    public void runTest() {
        outputService.writeOut(Localizer.localize("question-student-name"));
        String studentName = inputService.writeIn();

        dao.getQuestions().forEach(question -> {
            outputService.writeOut(question);
            String answer = inputService.writeIn();
            if (answer.equalsIgnoreCase(question.getRightAnswer())) rightCounts++;
        });

        outputService.writeOut(Localizer.localize(rightCounts>=appSettings.getRightAnswerNeedForPass()
                ? "test-result-success" : "test-result-fail", new Integer[]{rightCounts}));
    }
}
