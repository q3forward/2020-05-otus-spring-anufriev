package ru.otus.homework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.domain.jpa.Link;

public interface LinkRepository extends CrudRepository<Link, Long> {

    Link findByMongoIdAndClassName(String mongoId, String className);

}
