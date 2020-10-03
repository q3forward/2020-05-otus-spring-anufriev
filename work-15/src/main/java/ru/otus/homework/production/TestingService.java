package ru.otus.homework.production;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Program;

@Service
public class TestingService {

    /**
     * Тестирование программы. С вероятностью 50% завершается успешно.
     *
     */
    public Program testProgram(Program program) {
        program.setTestResult(Math.random() < 0.5);
        System.out.println("Результат тестирования: "+ (program.getTestResult() ? "успешно" : "найдены ошибки"));
        return program;
    }
}
