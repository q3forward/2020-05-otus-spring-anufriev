package ru.otus.homework.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.domain.Company;
import ru.otus.homework.domain.Drug;
import ru.otus.homework.service.ICompanyService;

import java.util.List;

@RestController
public class CompanyController {
    private final ICompanyService companyService;

    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = "/api/company")
    public List<Company> getCompanies() {
        return companyService.findAll();
    }

    @GetMapping(value = "/api/company/{id}")
    public Company getCompanyById(@PathVariable Long id) {
        return companyService.findById(id);
    }

    @DeleteMapping(value="/api/company/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id)  {
        companyService.delete(id);
        return null;
    }

    @PostMapping(value = "/api/company")
    public Company addCompany(@RequestBody Company company) {
        return companyService.add(company);
    }

    @PutMapping(value = "/api/company/{id}")
    public Company editCompany(@PathVariable Long id, @RequestBody Company company) {
        return companyService.update(id, company);
    }

    @GetMapping(value = "/api/refreshdata/{company}")
    public ResponseEntity<?> refreshData(@PathVariable String company) {
        return companyService.refreshDrugs(company);
    }
}
