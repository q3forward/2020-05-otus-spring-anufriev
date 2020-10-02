package ru.otus.homework.repository;

import reactor.core.publisher.Flux;

public interface CommentRepositoryCustom {

    Flux<String> getCommentIdsByBookId(String bookId);

}
