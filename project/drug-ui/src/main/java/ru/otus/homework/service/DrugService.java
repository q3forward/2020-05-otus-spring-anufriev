package ru.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Drug;
import ru.otus.homework.rest.DrugProxy;

import java.util.List;

@Service
public class DrugService implements IDrugService {
    private final DrugProxy drugProxy;

    public DrugService(DrugProxy drugProxy) {
        this.drugProxy = drugProxy;
    }

    @HystrixCommand
    @Override
    public List<Drug> findAll() {
        return drugProxy.getDrugs();
    }

    @HystrixCommand
    @Override
    public Drug add(Drug drug) {
        return drugProxy.addDrug(drug);
    }

    @HystrixCommand
    @Override
    public Drug update(Long drugId, Drug newDrug) {
        return drugProxy.editDrug(drugId, newDrug);
    }

    @HystrixCommand
    @Override
    public void delete(Long drugId) {
        drugProxy.deleteDrug(drugId);
    }

    @HystrixCommand
    @Override
    public Drug findById(Long drugId) {
        return drugProxy.getDrugById(drugId);
    }

}
