package ru.otus.homework.service;

import ru.otus.homework.domain.Company;
import ru.otus.homework.utils.exception.CompanyNotFoundException;

import java.util.List;

public interface CompanyService {
    Company add(Company drug);
    Company update(Long drugId, Company drug) throws CompanyNotFoundException;
    void delete(Long drugId);
    Company findById(Long drugId);
    List<Company> findAll();
}
