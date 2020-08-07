package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentService;
import ru.otus.homework.service.GenreService;
import ru.otus.homework.service.inout.InputService;
import ru.otus.homework.service.inout.OutputService;
import ru.otus.homework.utils.exception.BookNotFoundException;
import ru.otus.homework.utils.exception.CommentNotFoundException;

@ShellComponent
public class ShellCommands {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;
    private final OutputService outputService;
    private final InputService inputService;

    public ShellCommands(BookService bookService, AuthorService authorService, GenreService genreService, CommentService commentService,
                         OutputService outputService, InputService inputService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
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
        String bookId = inputService.writeIn();
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
        String bookId = inputService.writeIn();
        try {
            bookService.delete(bookId);
            commentService.deleteAllByBookId(bookId);
        } catch(BookNotFoundException e) {
            outputService.writeOut(e.getMessage());
        }
    }

    @ShellMethod(value="get", key={"g","get"})
    public void getBook() {
        outputService.writeOut("Введите id книги:");
        String bookId = inputService.writeIn();
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

    /*
        КОМАНДЫ ДЛЯ КОММЕНТАРИЕВ
    */

    @ShellMethod(value="commentList", key={"cl"})
    public void commentList() {
        outputService.writeOut(commentService.getAll());
    }

    @ShellMethod(value="getComment", key={"cg"})
    public void getComment() {
        outputService.writeOut("Введите id комментария:");
        String commentId = inputService.writeIn();
        Comment comment = commentService.getById(commentId);
        outputService.writeOut(comment!=null ? comment : "Комментарий с таким id не найден");
    }

    @ShellMethod(value="getCommentsForBook", key={"cgb"})
    public void getCommentsForBook() {
        outputService.writeOut("Введите id книги:");
        String bookId = inputService.writeIn();
        try {
            outputService.writeOut(commentService.getBookComments(bookId));
        } catch(BookNotFoundException e) {
            outputService.writeOut(e.getMessage());
        }
    }

    @ShellMethod(value="commentCreate", key={"cc"})
    public void createComment() {
        outputService.writeOut("Введите id книги:");
        String bookId = inputService.writeIn();
        outputService.writeOut("Введите текст комментария:");
        String text = inputService.writeIn();
        try {
            commentService.add(bookId, text);
        } catch (BookNotFoundException e) {
            outputService.writeOut(e.getMessage());
        }
    }

    @ShellMethod(value="commentUpdate", key={"cu"})
    public void updateComment() {
        outputService.writeOut("Введите id комментария:");
        String commentId = inputService.writeIn();
        outputService.writeOut("Введите id книги:");
        String bookId = inputService.writeIn();
        outputService.writeOut("Введите новый текст комментария:");
        String text = inputService.writeIn();
        try {
            commentService.update(commentId, bookId, text);
        } catch(BookNotFoundException | CommentNotFoundException e) {
            outputService.writeOut(e.getMessage());
        }
    }

    @ShellMethod(value="commentDelete", key={"cd"})
    public void deleteComment() {
        outputService.writeOut("Введите id комментария:");
        String commentId = inputService.writeIn();
        try {
            commentService.delete(commentId);
        } catch(CommentNotFoundException e) {
            outputService.writeOut(e.getMessage());
        }
    }

}
