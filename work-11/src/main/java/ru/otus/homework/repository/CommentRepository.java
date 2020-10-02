package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.homework.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String>, CommentRepositoryCustom {

    Flux<Comment> getCommentsByBookId(String bookId);

    //Flux<Void> deleteByIdIn(List<String> ids);

    //Flux<Void> deleteByBookId(String bookId);
}
