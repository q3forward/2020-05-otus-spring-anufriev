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
@Document(collection="authors")
public class MongoAuthor implements HaveId<String> {
    @Id
    private String id;
    private String name;

    public MongoAuthor(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
