package ru.otus.homework.utils.exception;

import ru.otus.homework.service.OutputService;
import ru.otus.homework.utils.BeanUtil;

public class IncorrectQuestionException extends RuntimeException {

    public IncorrectQuestionException(Exception e) {
        OutputService outputService = BeanUtil.getBean(OutputService.class);
        outputService.writeOut("Ошибка при формировании вопроса. Попробуйте пройти тест позже");
        initCause(e);
    }
}
