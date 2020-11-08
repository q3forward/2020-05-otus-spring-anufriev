package ru.otus.homework.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.domain.Company;
import ru.otus.homework.service.CompanyService;
import ru.otus.homework.utils.exception.CompanyNotFoundException;

@RestController
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = "/api/company")
    public Iterable<Company> getCompanys() {
        return companyService.findAll();
    }

    @GetMapping(value = "/api/company/{id}")
    public Company getCompanyById(@PathVariable Long id) {
        return companyService.findById(id);
    }

    @DeleteMapping(value="/api/company/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        companyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/company")
    public ResponseEntity<?> addCompany(@RequestBody Company company) {
        companyService.add(company);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/company/{id}")
    public ResponseEntity<?> editCompany(@PathVariable Long id, @RequestBody Company newСompany) {
        try {
            companyService.update(id, newСompany);
            return ResponseEntity.ok().build();
        } catch (CompanyNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
    
}
