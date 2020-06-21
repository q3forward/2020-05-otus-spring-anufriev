package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class OutputConsoleService implements OutputService {

    private final PrintStream outputStream;

    public OutputConsoleService( @Value("#{ T(java.lang.System).out}") PrintStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void writeOut(Object obj) {
        outputStream.println(obj);
    }
}
