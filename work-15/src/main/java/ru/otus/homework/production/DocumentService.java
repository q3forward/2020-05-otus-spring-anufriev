package ru.otus.homework.production;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Manual;
import ru.otus.homework.domain.Program;

@Service
public class DocumentService {
    public Program writeManual(Program program) {
        program.setManual(new Manual("Руководство пользователя"));
        System.out.println("Мануал написан");
        return program;
    }
}

