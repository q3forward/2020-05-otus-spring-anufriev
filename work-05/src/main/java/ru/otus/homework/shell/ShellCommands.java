package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.domain.Book;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;
import ru.otus.homework.service.inout.InputService;
import ru.otus.homework.service.inout.OutputService;
import ru.otus.homework.utils.exception.BookNotFoundException;

@ShellComponent
public class ShellCommands {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final OutputService outputService;
    private final InputService inputService;

    public ShellCommands(BookService bookService, AuthorService authorService, GenreService genreService, OutputService outputService, InputService inputService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.outputService = outputService;
        this.inputService = inputService;
    }

    @ShellMethod(value="create", key={"i","c","create"})
    public void createBook() {
        outputService.writeOut("Введите название книги:");
        String bookTitle = inputService.writeIn();
        outputService.writeOut("Введите автора:");
        String author = inputService.writeIn();
        outputService.writeOut("Введите жанр:");
        String genre = inputService.writeIn();
        bookService.add(bookTitle, author, genre);
    }

    @ShellMethod(value="update", key={"u","update"})
    public void updateBook() {
        outputService.writeOut("Введите id книги:");
        long bookId = Long.parseLong(inputService.writeIn());
        outputService.writeOut("Введите новое название книги:");
        String bookTitle = inputService.writeIn();
        outputService.writeOut("Введите нового автора:");
        String author = inputService.writeIn();
        outputService.writeOut("Введите новый жанр:");
        String genre = inputService.writeIn();
        try {
            bookService.update(bookId, bookTitle, author, genre);
        } catch(BookNotFoundException e) {
            outputService.writeOut(e.getMessage());
        }
    }

    @ShellMethod(value="delete", key={"d","delete"})
    public void deleteBook() {
        outputService.writeOut("Введите id книги:");
        long bookId = Long.parseLong(inputService.writeIn());
        try {
            bookService.delete(bookId);
        } catch(BookNotFoundException e) {
            outputService.writeOut(e.getMessage());
        }
    }

    @ShellMethod(value="get", key={"g","get"})
    public void getBook() {
        outputService.writeOut("Введите id книги:");
        long bookId = Long.parseLong(inputService.writeIn());
        Book book = bookService.getById(bookId);
        outputService.writeOut(book!=null ? book : "Книга с таким id не найдена");
    }

    @ShellMethod(value="list", key={"l","list"})
    public void bookList() {
        outputService.writeOut(bookService.getAll());
    }

    @ShellMethod(value="authorList", key={"al"})
    public void authorList() {
        outputService.writeOut(authorService.findAll());
    }

    @ShellMethod(value="genreList", key={"gl"})
    public void genreList() {
        outputService.writeOut(genreService.findAll());
    }
}
