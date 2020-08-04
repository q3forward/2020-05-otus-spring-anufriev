package ru.otus.homework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.domain.Book;

import java.util.List;


public interface BookRepository extends CrudRepository<Book, String> {

    List<Book> findAll();
}
