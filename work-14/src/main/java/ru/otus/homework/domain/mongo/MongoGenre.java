package ru.otus.homework.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.homework.domain.HaveId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="genres")
public class MongoGenre implements HaveId<String> {
    @Id
    private String id;
    private String name;

    public MongoGenre(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
