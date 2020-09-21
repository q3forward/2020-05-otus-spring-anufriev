package ru.otus.homework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import ru.otus.homework.domain.Comment;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Flux<String> getCommentIdsByBookId(String bookId) {
        Query query = new Query(Criteria.where("book._id").is(bookId));
        query.fields().include("_id");
        return mongoTemplate.find(query, Comment.class).map(Comment::getId);
    }
}
