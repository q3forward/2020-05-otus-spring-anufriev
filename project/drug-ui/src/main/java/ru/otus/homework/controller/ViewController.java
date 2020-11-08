package ru.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    /*** ПРЕПАРАТЫ ***/

    @GetMapping("/")
    public String listDrugView() {
        return "listDrug";
    }

    @GetMapping("/addDrug")
    public String addDrugView() {
        return "addDrug";
    }

    @GetMapping("/editDrug")
    public String editDrugView(@RequestParam("id") Long id, Model model) {
        model.addAttribute("drugId", id);
        return "editDrug";
    }

    /*** КОМПАНИИ ***/

    @GetMapping("/listCompany")
    public String listCompaniesView(Model model) {
        return "listCompany";
    }

    @GetMapping("/addCompany")
    public String addCompanyView(Model model) {
        return "addCompany";
    }

    @GetMapping("/editCompany")
    public String editCompanyView(@RequestParam("id") Long companyId, Model model) {
        model.addAttribute("companyId", companyId);
        return "editCompany";
    }
}
