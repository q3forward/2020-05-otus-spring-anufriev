package ru.otus.homework.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.dto.CommentDto;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.CommentRepository;

@RestController
public class CommentController {
    private final CommentRepository commentRepo;
    private final BookRepository bookRepo;

    @Autowired
    public CommentController(CommentRepository commentRepo, BookRepository bookRepo) {
        this.bookRepo = bookRepo;
        this.commentRepo = commentRepo;
    }

    @GetMapping(value = "/api/comment/book/{bookId}")
    public Flux<Comment> getCommentsByBook(@PathVariable String bookId) {
        return commentRepo.getCommentsByBookId(bookId);
    }

    @GetMapping(value = "/api/comment/{id}")
    public Mono<Comment> getCommentById(@PathVariable String id) {
        return commentRepo.findById(id);
    }

    @DeleteMapping(value="/api/comment/{id}")
    public Mono<ResponseEntity<Void>> deleteComment(@PathVariable String id) {
        return commentRepo.findById(id)
                .flatMap(comment -> commentRepo.delete(comment)
                        .then(Mono.just(ResponseEntity.ok().<Void>build()))
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/comment")
    public Mono<ResponseEntity<?>> addComment(@RequestBody CommentDto commentDto) {
        Comment comment = new Comment();
        return bookRepo.findById(commentDto.getBookId())
                    .flatMap(book -> {
                        comment.setBook(book);
                        comment.setText(commentDto.getText());
                        return commentRepo.save(comment).then(Mono.just(ResponseEntity.ok().<Void>build()));
                    }).then(Mono.just(ResponseEntity.ok().<Void>build()));
    }

    @PutMapping("/api/comment/{id}")
    public Mono<ResponseEntity<Void>> editComment(@PathVariable String id, @RequestBody CommentDto commentDto) {
        Comment comment = new Comment();
        return commentRepo.findById(id).flatMap(foundComment -> {
            comment.setId(foundComment.getId());
            comment.setText(commentDto.getText());
            return bookRepo.findById(commentDto.getBookId())
                    .flatMap(book -> {
                        comment.setBook(book);
                        return commentRepo.save(comment).then(Mono.just(ResponseEntity.ok().<Void>build()));
                    }).then(Mono.just(ResponseEntity.ok().<Void>build()));
        }).defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
