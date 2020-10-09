package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.repository.CommentRepository;
import ru.otus.homework.utils.exception.CommentNotFoundException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepo;

    public CommentServiceImpl(CommentRepository commentRepo) {
        this.commentRepo = commentRepo;
    }

    @Transactional
    @Override
    public Comment add(Book book, String text) {
        Comment comment = new Comment();
        comment.setText(text);
        setBookForComment(comment, book);
        return commentRepo.save(comment);
    }

    @Transactional
    @Override
    public void update(long commentId, Book book, String text) throws CommentNotFoundException {
        Optional<Comment> optComment = commentRepo.findById(commentId);
        if (optComment.isPresent()) {
            Comment comment = optComment.get();
            if (!text.equals("")) {
                comment.setText(text);
            }
            setBookForComment(comment, book);
            commentRepo.save(comment);
        } else {
            throw new CommentNotFoundException(String.format("Комментарий c Id=%d не найден", commentId));
        }
    }

    @Transactional
    @Override
    public void delete(long commentId) throws CommentNotFoundException {
        if (commentRepo.existsById(commentId)) {
            commentRepo.deleteById(commentId);
        } else {
            throw new CommentNotFoundException(String.format("Комментарий c Id=%d не найден", commentId));
        }
    }

    @Transactional
    @Override
    public void deleteAllByBookId(long bookId) {
        commentRepo.deleteByBookId(bookId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAll() {
        return commentRepo.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Comment getById(long commentId) {
        return commentRepo.findById(commentId).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getBookComments(long bookId) {
        if (bookId!=0) {
            return commentRepo.getCommentsByBookId(bookId);
        } else {
            return new ArrayList<>();
        }
    }

    private void setBookForComment(Comment comment, Book book) {
        if (book != null) {
            comment.setBook(book);
        }
    }
}
