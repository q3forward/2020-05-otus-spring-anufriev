package ru.otus.homework.mongock;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;

@ChangeLog
public class DatabaseChangelog {

    //////////// Genres
    private Genre genre1;
    private Genre genre2;
    private Genre genre3;

    @ChangeSet(order = "001", id = "1", author = "q3Forward")
    public void initGenres(MongoTemplate template) {
        genre1 = template.save(new Genre("Классическая проза"));
        genre2 = template.save(new Genre("Фэнтези"));
        genre3 = template.save(new Genre("Научно-популярное"));
    }

    //////////// Authors
    private Author author1;
    private Author author2;
    private Author author3;

    @ChangeSet(order = "010", id = "10", author = "q3Forward")
    public void initAuthors(MongoTemplate template) {
        author1 = template.save(new Author("Ремарк"));
        author2 = template.save(new Author("Толкиен"));
        author3 = template.save(new Author("Азимов"));
    }

    //////////// Books
    private Book book1;
    private Book book2;

    @ChangeSet(order = "020", id = "20", author = "q3Forward")
    public void initBooks(MongoTemplate template) {
        template.save(new Book("Три товарища", author1, genre1));
        book1 = template.save(new Book("Черный обелиск", author1, genre1));
        book2 = template.save(new Book("Властелин колец", author2, genre2));
        template.save(new Book("Загадки мироздания", author3, genre3));
    }

    //////////// Comments
    @ChangeSet(order = "030", id = "30", author = "q3Forward")
    public void initComments(MongoTemplate template) {
        template.save(new Comment("Очень хорошая книга", book1));
        template.save(new Comment("Скукота", book1));
        template.save(new Comment("Леголас рулит", book2));
    }

}
