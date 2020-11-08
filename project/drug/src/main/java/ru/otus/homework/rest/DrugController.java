package ru.otus.homework.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.domain.Drug;
import ru.otus.homework.service.DrugService;
import ru.otus.homework.utils.exception.DrugNotFoundException;

@RestController
public class DrugController {
    private final DrugService drugService;

    @Autowired
    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }

    @GetMapping(value = "/api/drug")
    public Iterable<Drug> getDrugs() {
        return drugService.findAll();
    }

    @GetMapping(value = "/api/drug/{id}")
    public Drug getDrugById(@PathVariable Long id) {
        return drugService.findById(id);
    }

    @DeleteMapping(value="/api/drug/{id}")
    public ResponseEntity<?> deleteDrug(@PathVariable Long id) {
        drugService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/drug")
    public ResponseEntity<?> addDrug(@RequestBody Drug drug) {
        drugService.add(drug);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/drug/{id}")
    public ResponseEntity<?> editDrug(@PathVariable Long id, @RequestBody Drug newdrug) {
        try {
            drugService.update(id, newdrug);
            return ResponseEntity.ok().build();
        } catch (DrugNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).build();
        }

    }
    
}
