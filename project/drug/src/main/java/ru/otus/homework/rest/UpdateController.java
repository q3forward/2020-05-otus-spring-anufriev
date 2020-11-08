package ru.otus.homework.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import ru.otus.homework.domain.Drug;
import ru.otus.homework.service.UpdateService;

import java.util.List;

@RestController
public class UpdateController {

    private final UpdateService updateService;

    public UpdateController(UpdateService updateService) {
        this.updateService = updateService;
    }

    @GetMapping(value = "/api/update/{company}")
    public List<Drug> updateDrugs(@PathVariable String company) {
        return updateService.update(company);
    }

    @GetMapping(value = "/api/refreshdata/{company}")
    public ResponseEntity<?> refreshDrugs(@PathVariable String company) {
        try {
            updateService.refreshDrugs(company);
            return ResponseEntity.ok().build();
        } catch(RestClientException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
