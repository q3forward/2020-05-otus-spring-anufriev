package ru.otus.homework.service.inout;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Scanner;

@Service
public class InputConsoleService implements InputService{

    private final InputStream inputStream;

    public InputConsoleService(@Value("#{ T(java.lang.System).in}") InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public String writeIn() {
        Scanner scanner = new Scanner(inputStream);
        return scanner.nextLine();
    }
}
