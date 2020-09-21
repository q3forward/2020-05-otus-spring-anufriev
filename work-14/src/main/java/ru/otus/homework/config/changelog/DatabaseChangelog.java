package ru.otus.homework.config.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import ru.otus.homework.domain.mongo.MongoAuthor;
import ru.otus.homework.domain.mongo.MongoBook;
import ru.otus.homework.domain.mongo.MongoComment;
import ru.otus.homework.domain.mongo.MongoGenre;

@ChangeLog
public class DatabaseChangelog {

    //////////// Genres
    private MongoGenre genre1;
    private MongoGenre genre2;
    private MongoGenre genre3;

    @ChangeSet(order = "001", id = "1", author = "q3Forward")
    public void initGenres(MongockTemplate template) {
        genre1 = template.save(new MongoGenre("Классическая проза"));
        genre2 = template.save(new MongoGenre("Фэнтези"));
        genre3 = template.save(new MongoGenre("Научно-популярное"));
    }

    //////////// Authors
    private MongoAuthor author1;
    private MongoAuthor author2;
    private MongoAuthor author3;

    @ChangeSet(order = "010", id = "10", author = "q3Forward")
    public void initAuthors(MongockTemplate template) {
        author1 = template.save(new MongoAuthor("Ремарк"));
        author2 = template.save(new MongoAuthor("Толкиен"));
        author3 = template.save(new MongoAuthor("Азимов"));
    }

    //////////// Books
    private MongoBook book1;
    private MongoBook book2;

    @ChangeSet(order = "020", id = "20", author = "q3Forward")
    public void initBooks(MongockTemplate template) {
        book1 = template.save(new MongoBook("Три товарища", author1, genre1));
        template.save(new MongoBook("Черный обелиск", author1, genre1));
        book2 = template.save(new MongoBook("Властелин колец", author2, genre2));
        template.save(new MongoBook("Загадки мироздания", author3, genre3));
    }

    //////////// Comments
    @ChangeSet(order = "030", id = "30", author = "q3Forward")
    public void initComments(MongockTemplate template) {
        template.save(new MongoComment("Очень хорошая книга", book1));
        template.save(new MongoComment("Скукота", book1));
        template.save(new MongoComment("Леголас рулит", book2));
    }

}
