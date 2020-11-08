package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.Drug;

public interface DrugRepository extends JpaRepository<Drug, Long> {
}
