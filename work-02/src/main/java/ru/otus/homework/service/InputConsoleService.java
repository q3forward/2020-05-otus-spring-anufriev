package ru.otus.homework.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class InputConsoleService implements InputService{

    @Override
    public String writeIn() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
