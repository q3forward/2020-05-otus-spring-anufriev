package ru.otus.homework.dto;

import lombok.Data;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

@Data
public class BookDto extends Book {

    private long id;
    private String title;
    private Author author;
    private Genre genre;
    private String newAuthorName;
    private String newGenreName;

    public BookDto() {

    }

    public BookDto(Long id, String title, String newAuthorName, String newGenreName) {
        this.id = id;
        this.title = title;
        this.newAuthorName = newAuthorName;
        this.newGenreName = newGenreName;
    }

}
