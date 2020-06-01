package ru.otus.homework.service;

import ru.otus.homework.dao.QuestionDao;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;
    private final ConsoleService consoleService;

    public QuestionServiceImpl(QuestionDao dao, ConsoleService consoleService) {
        this.dao = dao;
        this.consoleService = consoleService;
    }

    @Override
    public void printQuestions() {
        dao.getQuestions().forEach(consoleService::writeOut);
    }
}
