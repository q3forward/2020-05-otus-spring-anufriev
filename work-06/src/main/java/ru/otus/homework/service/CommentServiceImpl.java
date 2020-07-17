package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.dao.CommentDao;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.utils.exception.BookNotFoundException;
import ru.otus.homework.utils.exception.CommentNotFoundException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentDao commentDao;
    private final BookService bookService;

    public CommentServiceImpl(CommentDao commentDao, BookService bookService) {
        this.commentDao = commentDao;
        this.bookService = bookService;
    }

    @Transactional
    @Override
    public Comment add(long bookId, String text) throws BookNotFoundException {
        Comment comment = new Comment();
        comment.setText(text);
        setBookForComment(comment, bookId);
        return commentDao.save(comment);
    }

    @Transactional
    @Override
    public void update(long commentId, long bookId, String text) throws CommentNotFoundException, BookNotFoundException {
        Optional<Comment> optComment = commentDao.getById(commentId);
        if (optComment.isPresent()) {
            Comment comment = optComment.get();
            if (!text.equals("")) {
                comment.setText(text);
            }
            setBookForComment(comment, bookId);
            commentDao.save(comment);
        } else {
            throw new CommentNotFoundException(String.format("Комментарий c Id=%d не найден", commentId));
        }
    }

    @Transactional
    @Override
    public void delete(long commentId) throws CommentNotFoundException {
        if (commentDao.existsById(commentId)) {
            commentDao.deleteById(commentId);
        } else {
            throw new CommentNotFoundException(String.format("Комментарий c Id=%d не найден", commentId));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAll() {
        return commentDao.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Comment getById(long commentId) {
        return commentDao.getById(commentId).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getBookComments(long bookId) throws BookNotFoundException {
        if (bookId!=0) {
            Book book = bookService.getById(bookId);
            if (book != null) {
                return commentDao.getCommentsByBookId(bookId);
            } else {
                throw new BookNotFoundException(String.format("Книга c Id=%d не найдена", bookId));
            }
        } else {
            return new ArrayList();
        }
    }

    private void setBookForComment(Comment comment, long bookId) throws BookNotFoundException{
        if (bookId!=0) {
            Book book = bookService.getById(bookId);
            if (book != null) {
                comment.setBook(book);
            } else {
                throw new BookNotFoundException(String.format("Книга c Id=%d не найдена", bookId));
            }
        }
    }
}
