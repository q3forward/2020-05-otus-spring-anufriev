package ru.otus.homework.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Book;
import ru.otus.homework.repository.BookRepository;

@Component
public class BookComplaintsHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepo;

    public BookComplaintsHealthIndicator(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public Health health() {
        Book book = bookRepo.findByTitle("Книга жалоб");
        if (book==null) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Отсутствует книга жалоб! Работа библиотеки невозможна!")
                    .build();
        } else {
            return Health.up().withDetail("message", "Держите ваше книгу жалоб: "+book).build();
        }
    }
}
