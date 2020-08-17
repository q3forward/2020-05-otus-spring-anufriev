package ru.otus.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.service.AuthorService;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.GenreService;
import ru.otus.homework.utils.exception.BookNotFoundException;

import java.util.List;
import java.util.Map;

@Controller
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping("/")
    public String listBook(Model model) {
        List<Book> books = bookService.getAll();
        model.addAttribute("books", books);
        return "listBook";
    }

    @GetMapping("/addBook")
    public String addBook(Model model) {
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "addBook";
    }

    @GetMapping("/editBook")
    public String editBook(@RequestParam("id") Long id, Model model) throws BookNotFoundException {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("choosedAuthorId", book.getAuthor().getId());
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("choosedGenreId", book.getGenre().getId());
        return "editBook";
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam("id") Long id) throws BookNotFoundException {
        bookService.delete(id);
        return "redirect:/";
    }

    @PostMapping("/addBook")
    public String addBook(Book book) {
        bookService.add(book.getTitle(), book.getAuthor().getName(), book.getGenre().getName());
        return "redirect:/";
    }

    @PostMapping("/editBook")
    public String editBook(@RequestParam Map<String, String> params, Long id, String title, @RequestParam Author author, @RequestParam Genre genre) throws BookNotFoundException {
        String authorName;
        String genreName;

        if (author==null) {
            authorName = authorService.add(params.get("newAuthor")).getName();
        } else {
            authorName = author.getName();
        }

        if (genre==null) {
            genreName = genreService.add(params.get("newGenre")).getName();
        } else {
            genreName = genre.getName();
        }
        bookService.update(id, title, authorName, genreName);
        return "redirect:/";
    }
}
