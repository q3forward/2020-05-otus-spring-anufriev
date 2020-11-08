package ru.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Drug;
import ru.otus.homework.repository.DrugRepository;
import ru.otus.homework.utils.exception.DrugNotFoundException;

import java.util.List;

@Service
public class DrugServiceImpl implements DrugService {
    private final DrugRepository drugRepo;

    public DrugServiceImpl(DrugRepository drugRepo) {
        this.drugRepo = drugRepo;
    }

    @HystrixCommand
    @Override
    @Transactional
    public Drug add(Drug drug) {
        return drugRepo.save(drug);
    }

    @HystrixCommand
    @Override
    @Transactional
    public Drug update(Long drugId, Drug newDrug) throws DrugNotFoundException {
        Drug savedDrug = null;
        Drug existDrug = drugRepo.findById(drugId).orElseThrow(() -> new DrugNotFoundException("Препарат по указанному Id не найден"));
        if (existDrug!=null) {
            existDrug.setName(newDrug.getName());
            existDrug.setArea(newDrug.getArea());
            existDrug.setPhase(newDrug.getPhase());
            existDrug.setLink(newDrug.getLink());
            existDrug.setDescription(newDrug.getDescription());
            existDrug.setCompany(newDrug.getCompany());
            savedDrug = drugRepo.save(existDrug);
        }
        return savedDrug;
    }

    @HystrixCommand
    @Override
    @Transactional
    public void delete(Long drugId) {
        drugRepo.deleteById(drugId);
    }

    @HystrixCommand
    @Transactional(readOnly = true)
    @Override
    public Drug findById(Long drugId) {
        return drugRepo.findById(drugId).orElse(null);
    }

    @HystrixCommand
    @Transactional(readOnly = true)
    @Override
    public List<Drug> findAll() {
        return drugRepo.findAll(Sort.by(Sort.Direction.DESC, "phase"));
    }
}
