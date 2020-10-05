package ru.otus.homework.production;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Concept;
import ru.otus.homework.domain.Program;

@Service
public class DevelopService {

    public Program code(Concept concept) {
        Program program = new Program("Код программы");
        program.setConcept(concept);
        System.out.println("Написание кода завершено");
        return program;
    }

}
