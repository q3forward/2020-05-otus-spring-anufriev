package ru.otus.homework.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="comments")
public class MongoComment {
    @Id
    private String id;
    private String text;

    @DBRef
    private MongoBook book;

    public MongoComment(String text, MongoBook book) {
        this.text = text;
        this.book = book;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text=" + text +
                ", book=" + book.getTitle() +
                '}';
    }
}
