package ru.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Company;
import ru.otus.homework.repository.CompanyRepository;
import ru.otus.homework.utils.exception.CompanyNotFoundException;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepo;

    public CompanyServiceImpl(CompanyRepository companyRepo) {
        this.companyRepo = companyRepo;
    }

    @HystrixCommand
    @Override
    @Transactional
    public Company add(Company company) {
        return companyRepo.save(company);
    }

    @HystrixCommand
    @Override
    @Transactional
    public Company update(Long id, Company newCompany) throws CompanyNotFoundException {
        Company savedCompany = null;
        Company existCompany = companyRepo.findById(id).orElseThrow(() -> new CompanyNotFoundException("Компания по указанному Id не найдена"));
        if (existCompany!=null) {
            existCompany.setName(newCompany.getName());
            existCompany.setBrief(newCompany.getBrief());
            existCompany.setCountry(newCompany.getCountry());
            existCompany.setLink(newCompany.getLink());
            existCompany.setYear(newCompany.getYear());
            savedCompany = companyRepo.save(existCompany);
        }
        return savedCompany;
    }

    @HystrixCommand
    @Override
    @Transactional
    public void delete(Long drugId) {
        companyRepo.deleteById(drugId);
    }

    @HystrixCommand
    @Transactional(readOnly = true)
    @Override
    public Company findById(Long companyId) {
        return companyRepo.findById(companyId).orElse(null);
    }

    @HystrixCommand
    @Transactional(readOnly = true)
    @Override
    public List<Company> findAll() {
        return companyRepo.findAll();
    }

}
