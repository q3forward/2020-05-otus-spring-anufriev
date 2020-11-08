package ru.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import ru.otus.homework.domain.Company;
import ru.otus.homework.domain.Drug;
import ru.otus.homework.feign.UpdaterProxy;
import ru.otus.homework.repository.CompanyRepository;
import ru.otus.homework.repository.DrugRepository;

import java.util.List;

@Service
public class UpdateService {

    private final UpdaterProxy updaterProxy;
    private final DrugRepository drugRepo;
    private final CompanyRepository companyRepo;

    public UpdateService(UpdaterProxy updaterProxy, DrugRepository drugRepo, CompanyRepository companyRepo) {
        this.updaterProxy = updaterProxy;
        this.drugRepo = drugRepo;
        this.companyRepo = companyRepo;
    }

    @HystrixCommand
    public List<Drug> update(String companyBrief) throws RestClientException {
        Company c = companyRepo.findByBrief(companyBrief);
        List<Drug> list = updaterProxy.getExternalData(companyBrief);
        list.stream().forEach(d -> d.setCompany(c));
        return drugRepo.saveAll(list);
    }

    @HystrixCommand
    public void refreshDrugs(String companyBrief) throws RestClientException {
        update(companyBrief);
    }
}
