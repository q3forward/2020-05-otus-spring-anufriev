package ru.otus.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentService;
import ru.otus.homework.utils.exception.BookNotFoundException;
import ru.otus.homework.utils.exception.CommentNotFoundException;

import java.util.List;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final BookService bookService;

    @Autowired
    public CommentController(CommentService commentService, BookService bookService) {
        this.commentService = commentService;
        this.bookService = bookService;
    }

    @GetMapping("/listComment")
    public String listComment(@RequestParam("id") Long bookId, Model model) throws BookNotFoundException {
        List<Comment> comments = commentService.getBookComments(bookId);
        Book book = bookService.getById(bookId);
        model.addAttribute("comments", comments);
        model.addAttribute("bookId", bookId);
        model.addAttribute("bookTitle", book.getTitle());
        return "listComment";
    }

    @GetMapping("/addComment")
    public String addComment(@RequestParam("bookId") Long bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "addComment";
    }

    @GetMapping("/editComment")
    public String editComment(@RequestParam("id") Long id, Model model) {
        Comment comment = commentService.getById(id);
        model.addAttribute("id", comment.getId());
        model.addAttribute("bookId", comment.getBook().getId());
        model.addAttribute("text", comment.getText());
        return "editComment";
    }

    @PostMapping("/addComment")
    public ModelAndView addComment(@RequestParam("text") String commentText, @RequestParam("bookId") Long bookId, ModelMap model) {
        commentService.add(bookService.getById(bookId), commentText);
        model.addAttribute("id", bookId);
        return new ModelAndView("redirect:/listComment", model);
    }

    @PostMapping("/editComment")
    public ModelAndView editComment(@RequestParam("id") Long id, @RequestParam("text") String commentText, @RequestParam("bookId") Long bookId, ModelMap model) throws CommentNotFoundException {
        commentService.update(id, bookService.getById(bookId), commentText);
        model.addAttribute("id", bookId);
        return new ModelAndView("redirect:/listComment", model);
    }

    @GetMapping("/deleteComment")
    public ModelAndView deleteComment(@RequestParam("id") Long id, ModelMap model) throws CommentNotFoundException {
        Long bookId = commentService.getById(id).getBook().getId();
        commentService.delete(id);
        model.addAttribute("id", bookId);
        return new ModelAndView("redirect:/listComment", model);
    }
}
