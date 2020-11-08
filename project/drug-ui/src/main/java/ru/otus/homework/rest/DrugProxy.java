package ru.otus.homework.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.domain.Company;
import ru.otus.homework.domain.Drug;

import java.util.List;

@FeignClient(name = "drug")
public interface DrugProxy {

    @GetMapping(value = "/api/drug")
    List<Drug> getDrugs();

    @GetMapping(value = "/api/drug/{id}")
    Drug getDrugById(@PathVariable Long id);

    @PostMapping(value = "/api/drug")
    Drug addDrug(Drug drug);

    @PutMapping(value = "/api/drug/{id}")
    Drug editDrug(@PathVariable Long id, Drug drug);

    @DeleteMapping(value = "/api/drug/{id}")
    Drug deleteDrug(@PathVariable Long id);

    /////////////////////////////////////////

    @GetMapping(value = "/api/company")
    List<Company> getCompanies();

    @GetMapping(value = "/api/company/{id}")
    Company getCompanyById(@PathVariable Long id);

    @PostMapping(value = "/api/company")
    Company addCompany(Company company);

    @PutMapping(value = "/api/company/{id}")
    Company editCompany(@PathVariable Long id, Company company);

    @DeleteMapping(value = "/api/company/{id}")
    void deleteCompany(@PathVariable Long id);

    ////////////////////////////////////////////

    @GetMapping(value = "/api/refreshdata/{company}")
    ResponseEntity<?> refreshData(@PathVariable String company);
}