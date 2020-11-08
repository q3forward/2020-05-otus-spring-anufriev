package ru.otus.homework.rest;

import org.springframework.web.bind.annotation.*;
import ru.otus.homework.domain.DrugDto;
import ru.otus.homework.service.IReceiveService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DataController {
    private final IReceiveService abbvieReceiveServ;
    private final IReceiveService bmsReceiveServ;

    public DataController(IReceiveService abbvieReceiveServ, IReceiveService bmsReceiveServ) {
        this.abbvieReceiveServ = abbvieReceiveServ;
        this.bmsReceiveServ = bmsReceiveServ;
    }

    @GetMapping(value = "/api/update")
    public List<DrugDto> getDataFromUrl(@RequestParam String company) {
         if (company.equals("abbvie")) {
             return abbvieReceiveServ.getData();
         } else if (company.equals("bms")) {
             return bmsReceiveServ.getData();
         } else {
             return null;
         }
    }
}
