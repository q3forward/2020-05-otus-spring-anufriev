package ru.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    /*** КНИГИ ***/

    @GetMapping("/")
    public String listBookView() {
        return "listBook";
    }

    @GetMapping("/addBook")
    public String addBookView() {
        return "addBook";
    }

    @GetMapping("/editBook")
    public String editBookView(@RequestParam("id") Long id, Model model) {
        model.addAttribute("bookId", id);
        return "editBook";
    }

    /*** КОММЕНТАРИИ ***/

    @GetMapping("/listComment")
    public String listCommentView(@RequestParam("bookId") Long bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "listComment";
    }

    @GetMapping("/addComment")
    public String addCommentView(@RequestParam("bookId") Long bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "addComment";
    }

    @GetMapping("/editComment")
    public String editCommentView(@RequestParam("id") Long id, @RequestParam("bookId") Long bookId, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("bookId", bookId);
        return "editComment";
    }
}
