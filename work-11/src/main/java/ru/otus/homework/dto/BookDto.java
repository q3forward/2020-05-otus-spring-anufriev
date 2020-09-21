package ru.otus.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

@Data
public class BookDto {

    private String id;
    private String title;
    private Author author;
    private Genre genre;
    private String newAuthorName;
    private String newGenreName;

    public BookDto() {

    }

    public BookDto(String id, String title, String newAuthorName, String newGenreName) {
        this.id = id;
        this.title = title;
        this.newAuthorName = newAuthorName;
        this.newGenreName = newGenreName;
    }

}
