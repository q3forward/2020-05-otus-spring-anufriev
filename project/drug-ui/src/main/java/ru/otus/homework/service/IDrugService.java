package ru.otus.homework.service;

import ru.otus.homework.domain.Drug;

import java.util.List;

public interface IDrugService {
    Drug add(Drug drug);
    Drug update(Long drugId, Drug drug);
    void delete(Long drugId);
    Drug findById(Long drugId);
    List<Drug> findAll();
}
