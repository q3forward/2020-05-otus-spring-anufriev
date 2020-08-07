package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.repository.CommentRepository;
import ru.otus.homework.utils.exception.BookNotFoundException;
import ru.otus.homework.utils.exception.CommentNotFoundException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepo;
    private final BookService bookService;

    public CommentServiceImpl(CommentRepository commentRepo, BookService bookService) {
        this.commentRepo = commentRepo;
        this.bookService = bookService;
    }

    @Transactional
    @Override
    public Comment add(String bookId, String text) throws BookNotFoundException {
        Comment comment = new Comment();
        comment.setText(text);
        setBookForComment(comment, bookId);
        return commentRepo.save(comment);
    }

    @Transactional
    @Override
    public void update(String commentId, String bookId, String text) throws CommentNotFoundException, BookNotFoundException {
        Optional<Comment> optComment = commentRepo.findById(commentId);
        if (optComment.isPresent()) {
            Comment comment = optComment.get();
            if (!text.equals("")) {
                comment.setText(text);
            }
            setBookForComment(comment, bookId);
            commentRepo.save(comment);
        } else {
            throw new CommentNotFoundException(String.format("Комментарий c Id=%s не найден", commentId));
        }
    }

    @Transactional
    @Override
    public void delete(String commentId) throws CommentNotFoundException {
        if (commentRepo.existsById(commentId)) {
            commentRepo.deleteById(commentId);
        } else {
            throw new CommentNotFoundException(String.format("Комментарий c Id=%s не найден", commentId));
        }
    }

    @Transactional
    @Override
    public void deleteAllByBookId(String bookId) {
        if (bookId!=null && !bookId.equals("")) {
            commentRepo.deleteByIdIn(commentRepo.getCommentIdsByBookId(bookId));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAll() {
        return commentRepo.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Comment getById(String commentId) {
        return commentRepo.findById(commentId).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getBookComments(String bookId) throws BookNotFoundException {
        if (!bookId.equals("")) {
            Book book = bookService.getById(bookId);
            if (book != null) {
                return commentRepo.getCommentsByBookId(bookId);
            } else {
                throw new BookNotFoundException(String.format("Книга c Id=%s не найдена", bookId));
            }
        } else {
            return new ArrayList();
        }
    }

    @Override
    public List<String> getCommentIdsByBookId(String bookId) {
        return commentRepo.getCommentIdsByBookId(bookId);
    }

    private void setBookForComment(Comment comment, String bookId) throws BookNotFoundException{
        if (!bookId.equals("")) {
            Book book = bookService.getById(bookId);
            if (book != null) {
                comment.setBook(book);
            } else {
                throw new BookNotFoundException(String.format("Книга c Id=%s не найдена", bookId));
            }
        }
    }
}
