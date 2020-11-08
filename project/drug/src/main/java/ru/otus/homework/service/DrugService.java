package ru.otus.homework.service;

import ru.otus.homework.domain.Drug;
import ru.otus.homework.utils.exception.DrugNotFoundException;

import java.util.List;

public interface DrugService {
    Drug add(Drug drug);
    Drug update(Long drugId, Drug drug) throws DrugNotFoundException;
    void delete(Long drugId);
    Drug findById(Long drugId);
    List<Drug> findAll();
}
