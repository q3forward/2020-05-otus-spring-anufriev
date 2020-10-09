package ru.otus.homework.utils.exception;

public class CommentNotFoundException extends Exception {

    public CommentNotFoundException() {

    }

    public CommentNotFoundException(String message) {
        super(message);
    }
}
