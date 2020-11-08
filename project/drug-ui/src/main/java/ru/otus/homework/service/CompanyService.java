package ru.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Company;
import ru.otus.homework.rest.DrugProxy;

import java.util.List;

@Service
public class CompanyService implements ICompanyService {
    private final DrugProxy drugProxy;

    public CompanyService(DrugProxy drugProxy) {
        this.drugProxy = drugProxy;
    }

    @HystrixCommand
    @Override
    public List<Company> findAll() {
        return drugProxy.getCompanies();
    }

    @HystrixCommand
    @Override
    public Company add(Company company) {
        return drugProxy.addCompany(company);
    }

    @HystrixCommand
    @Override
    public Company update(Long id, Company newCompany) {
        return drugProxy.editCompany(id, newCompany);
    }

    @HystrixCommand
    @Override
    public void delete(Long id) {
        drugProxy.deleteCompany(id);
    }

    @HystrixCommand
    @Override
    public Company findById(Long id) {
        return drugProxy.getCompanyById(id);
    }

    @HystrixCommand
    public ResponseEntity<?> refreshDrugs(String company) {
        return drugProxy.refreshData(company);
    }

}
