package ru.otus.homework.service;

import java.io.PrintStream;

public class OutputConsoleService implements OutputService {

    private final PrintStream outputStream;

    public OutputConsoleService(PrintStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void writeOut(Object obj) {
        outputStream.println(obj);
    }
}
