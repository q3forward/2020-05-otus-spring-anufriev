package ru.otus.homework.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;

@Controller
public class ViewController {

    /*** КНИГИ ***/

    @GetMapping("/")
    public String listBookView(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        model.addAttribute("authorities", authentication.getAuthorities().stream().map(i -> i.getAuthority()).collect(Collectors.toList()));
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

    @GetMapping("/deleteBook")
    public String deleteBookView(@RequestParam("id") Long id, Model model) {
        model.addAttribute("bookId", id);
        return "editBook";
    }

    /*** КОММЕНТАРИИ ***/

    @GetMapping("/listComment")
    public String listCommentView(@RequestParam("bookId") Long bookId, Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        model.addAttribute("authorities", authentication.getAuthorities().stream().map(i -> i.getAuthority()).collect(Collectors.toList()));
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
