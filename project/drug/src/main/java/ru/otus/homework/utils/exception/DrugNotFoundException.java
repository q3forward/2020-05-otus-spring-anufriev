package ru.otus.homework.utils.exception;

public class DrugNotFoundException extends Exception {

    public DrugNotFoundException() {}

    public DrugNotFoundException(String message) {
        super(message);
    }
}
