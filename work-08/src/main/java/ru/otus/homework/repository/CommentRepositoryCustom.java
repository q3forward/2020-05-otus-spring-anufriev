package ru.otus.homework.repository;

import java.util.List;

public interface CommentRepositoryCustom {

    List<String> getCommentIdsByBookId(String bookId);

}
