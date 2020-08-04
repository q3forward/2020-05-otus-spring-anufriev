package ru.otus.homework.mongock.changelog;

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

    @ChangeSet(order = "001", id = "1", author = "q3Forward")
    public void initGenres(MongoTemplate template) {
        genre1 = template.save(new Genre("1","Test genre"));
        genre2 = template.save(new Genre("2","Test genre 2"));
        template.save(new Genre("3","Test genre 3"));
    }

    //////////// Authors
    private Author author1;
    private Author author2;

    @ChangeSet(order = "010", id = "10", author = "q3Forward")
    public void initAuthors(MongoTemplate template) {
        author1 = template.save(new Author("1","Test author"));
        author2 = template.save(new Author("2","Test author 2"));
        template.save(new Author("3","Test author 3"));
    }

    //////////// Books
    private Book book1;
    private Book book2;

    @ChangeSet(order = "020", id = "20", author = "q3Forward")
    public void initBooks(MongoTemplate template) {
        book1 = template.save(new Book("1","Book title", author1, genre1));
        book2 = template.save(new Book("2","Book title 2", author2, genre2));
    }

    //////////// Comments
    @ChangeSet(order = "030", id = "30", author = "q3Forward")
    public void initComments(MongoTemplate template) {
        template.save(new Comment("1","Comment", book1));
        template.save(new Comment("2", "Comment 2", book1));
        template.save(new Comment("3", "Comment 3", book2));
    }

}
