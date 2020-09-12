package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.service.*;
import ru.otus.homework.service.inout.OutputService;

@ShellComponent
public class ShellCommands {

    private final InfoService infoService;
    private final OutputService outputService;

    public ShellCommands(InfoService infoService, OutputService outputService) {
        this.infoService = infoService;
        this.outputService = outputService;
    }

    @ShellMethod(value="bookList", key={"bl"})
    public void bookList() {
        outputService.writeOut(infoService.outputBooks());
    }

    @ShellMethod(value="authorList", key={"al"})
    public void authorList() {
        outputService.writeOut(infoService.outputAuthors());
    }

    @ShellMethod(value="genreList", key={"gl"})
    public void genreList() {
        outputService.writeOut(infoService.outputGenres());
    }

    @ShellMethod(value="commentList", key={"cl"})
    public void commentList() {
        outputService.writeOut(infoService.outputComments());
    }

    @ShellMethod(value="linkList", key={"ll"})
    public void linkList() {
        outputService.writeOut(infoService.outputLinks());
    }

}
