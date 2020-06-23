package ru.otus.homework.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.homework.service.InputService;
import ru.otus.homework.service.Localizer;
import ru.otus.homework.service.OutputService;
import ru.otus.homework.service.QuestionService;

@ShellComponent
public class ShellCommands {

    private final QuestionService questionService;
    private final OutputService outputService;
    private final InputService inputService;
    private final Localizer localizer;

    private String studentName;
    private boolean isTestFinished = false;

    public ShellCommands(QuestionService questionService, OutputService outputService, InputService inputService, Localizer localizer) {
        this.questionService = questionService;
        this.outputService = outputService;
        this.inputService = inputService;
        this.localizer = localizer;
    }

    @ShellMethod(value="Login", key={"l","login"})
    public void login() {
        outputService.writeOut(localizer.localize("question-student-name"));
        this.studentName = inputService.writeIn();
    }

    @ShellMethod(value="Test", key={"t","test"})
    @ShellMethodAvailability(value="isStudentLogin")
    public void runTest() {
        questionService.runTest();
        isTestFinished = true;
    }

    @ShellMethod(value="Results", key={"r","res","results"})
    @ShellMethodAvailability(value="isTestFinished")
    public void showResults() {
        questionService.showResults();
    }

    private Availability isStudentLogin() {
        return studentName==null ? Availability.unavailable(localizer.localize("not-authorized")) : Availability.available();
    }
    private Availability isTestFinished() {
        return isTestFinished ? Availability.available() : Availability.unavailable(localizer.localize("not-tested"));
    }
}
