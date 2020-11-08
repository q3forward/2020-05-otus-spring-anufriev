package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByBrief(String brief);
}
