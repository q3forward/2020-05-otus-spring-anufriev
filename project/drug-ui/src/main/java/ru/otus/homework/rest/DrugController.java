package ru.otus.homework.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.domain.Drug;
import ru.otus.homework.service.IDrugService;

import java.util.List;

@RestController
public class DrugController {
    private final IDrugService drugService;

    public DrugController(IDrugService drugService) {
        this.drugService = drugService;
    }

    @GetMapping(value = "/api/drug")
    public List<Drug> getDrugs() {
        return drugService.findAll();
    }

    @GetMapping(value = "/api/drug/{id}")
    public Drug getDrugById(@PathVariable Long id) {
        return drugService.findById(id);
    }

    @DeleteMapping(value="/api/drug/{id}")
    public ResponseEntity<?> deleteDrug(@PathVariable Long id)  {
        drugService.delete(id);
        return null;
    }

    @PostMapping(value = "/api/drug")
    public Drug addDrug(@RequestBody Drug drug) {
        return drugService.add(drug);
    }

    @PutMapping(value = "/api/drug/{id}")
    public Drug editDrug(@PathVariable Long id, @RequestBody Drug drug) {
        return drugService.update(id, drug);
    }
}
