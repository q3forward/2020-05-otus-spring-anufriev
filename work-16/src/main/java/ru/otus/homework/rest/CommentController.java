package ru.otus.homework.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.dto.CommentDto;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentService;
import ru.otus.homework.utils.exception.BookNotFoundException;
import ru.otus.homework.utils.exception.CommentNotFoundException;

import java.util.List;

@RestController
public class CommentController {
    private final CommentService commentService;
    private final BookService bookService;

    @Autowired
    public CommentController(CommentService commentService, BookService bookService) {
        this.commentService = commentService;
        this.bookService = bookService;
    }

    @GetMapping(value = "/api/comment/book/{bookId}")
    public List<Comment> getCommentsByBook(@PathVariable Long bookId) throws BookNotFoundException {
        List<Comment> comments = commentService.getBookComments(bookId);
        return comments;
    }

    @GetMapping(value = "/api/comment/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return commentService.getById(id);
    }

    @DeleteMapping(value="/api/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) throws CommentNotFoundException {
        commentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/comment")
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto) {
        commentService.add(bookService.getById(commentDto.getBookId()), commentDto.getText());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/comment/{id}")
    public ResponseEntity<?> editComment(@PathVariable Long id, @RequestBody CommentDto commentDto) throws CommentNotFoundException {
        commentService.update(id, bookService.getById(commentDto.getBookId()), commentDto.getText());
        return ResponseEntity.ok().build();
    }
}
