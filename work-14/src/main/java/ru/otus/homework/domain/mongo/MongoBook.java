package ru.otus.homework.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.homework.domain.HaveId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="books")
public class MongoBook implements HaveId<String> {
    @Id
    private String id;
    private String title;

    @DBRef
    private MongoAuthor author;

    @DBRef
    private MongoGenre genre;

    public MongoBook(String title, MongoAuthor author, MongoGenre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title=" + title +
                ", author=" + author.getName() +
                ", genre=" + genre.getName() +
                '}';
    }
}
