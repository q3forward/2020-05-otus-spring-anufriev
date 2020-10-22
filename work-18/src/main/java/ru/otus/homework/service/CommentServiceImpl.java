package ru.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @HystrixCommand(fallbackMethod = "addComment_Fallback")
    @Transactional
    @Override
    public Comment add(Book book, String text) {
        Comment comment = new Comment();
        comment.setText(text);
        setBookForComment(comment, book);
        return commentRepo.save(comment);
    }

    @HystrixCommand(fallbackMethod = "updateComment_Fallback")
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

    @HystrixCommand(fallbackMethod = "deleteComment_Fallback")
    @Transactional
    @Override
    public void delete(long commentId) throws CommentNotFoundException {
        if (commentRepo.existsById(commentId)) {
            commentRepo.deleteById(commentId);
        } else {
            throw new CommentNotFoundException(String.format("Комментарий c Id=%d не найден", commentId));
        }
    }

    @HystrixCommand(fallbackMethod = "deleteAllByBookId_Fallback")
    @Transactional
    @Override
    public void deleteAllByBookId(long bookId) {
        commentRepo.deleteByBookId(bookId);
    }

    @HystrixCommand(fallbackMethod = "getAllComment_Fallback")
    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAll() {
        return commentRepo.findAll();
    }

    @HystrixCommand(fallbackMethod = "getByIdComment_Fallback")
    @Transactional(readOnly = true)
    @Override
    public Comment getById(long commentId) {
        return commentRepo.findById(commentId).orElse(null);
    }

    @HystrixCommand(fallbackMethod = "getBookComments_Fallback")
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

    private Comment addComment_Fallback(Book book, String text, Throwable e) {
        if (e!=null) System.out.println(e.getMessage());
        throw new RuntimeException("Something going wrong on add comment");
    }

    private void updateComment_Fallback(long commentId, Book book, String text, Throwable e) {
        if (e!=null) System.out.println(e.getMessage());
        throw new RuntimeException("Something going wrong on update comment");
    }

    private void deleteComment_Fallback(long commentId, Throwable e) {
        if (e!=null) System.out.println(e.getMessage());
        throw new RuntimeException("Something going wrong on delete comment");
    }

    private void deleteAllByBookId_Fallback(long bookId, Throwable e) {
        if (e!=null) System.out.println(e.getMessage());
        throw new RuntimeException("Something going wrong on delete comments");
    }

    private List<Comment> getAllComment_Fallback() {
        return new ArrayList();
    }

    private Comment getByIdComment_Fallback(long commentId) {
        Comment comment = new Comment();
        comment.setText("NA");
        return comment;
    }

    private List<Comment> getBookComments_Fallback(long Book) {
        return new ArrayList();
    }
}
