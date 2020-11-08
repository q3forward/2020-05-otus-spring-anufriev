package ru.otus.homework.service;

import org.springframework.http.ResponseEntity;
import ru.otus.homework.domain.Company;

import java.util.List;

public interface ICompanyService {
    Company add(Company drug);
    Company update(Long drugId, Company drug);
    void delete(Long drugId);
    Company findById(Long drugId);
    List<Company> findAll();
    ResponseEntity<?> refreshDrugs(String company);
}
