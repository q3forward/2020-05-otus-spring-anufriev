package ru.otus.homework.utils.exception;

public class IncorrectQuestionException extends RuntimeException {

    public IncorrectQuestionException(Exception e) {
        initCause(e);
    }
}
