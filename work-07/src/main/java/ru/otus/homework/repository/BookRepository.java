package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.domain.Book;

import java.util.List;


public interface BookRepository extends CrudRepository<Book, Long> {

    @Query("select b from Book b join fetch b.author join fetch b.genre")
    List<Book> findAll();
}
