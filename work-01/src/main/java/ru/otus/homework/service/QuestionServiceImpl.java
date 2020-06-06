package ru.otus.homework.service;

import ru.otus.homework.dao.QuestionDao;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;
    private final OutputConsoleService outputConsoleService;

    public QuestionServiceImpl(QuestionDao dao, OutputConsoleService outputConsoleService) {
        this.dao = dao;
        this.outputConsoleService = outputConsoleService;
    }

    @Override
    public void printQuestions() {
        dao.getQuestions().forEach(outputConsoleService::writeOut);
    }
}
