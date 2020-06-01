package ru.otus.homework.service;

import java.io.PrintStream;

public class ConsoleService {

    private final PrintStream outputStream;

    public ConsoleService(PrintStream outputStream) {
        this.outputStream = outputStream;
    }

    public void writeOut(Object obj) {
        outputStream.println(obj);
    }
}
