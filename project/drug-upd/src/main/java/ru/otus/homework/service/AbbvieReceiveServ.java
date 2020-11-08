package ru.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import ru.otus.homework.domain.DrugDto;
import ru.otus.homework.domain.abbvie.Asset;
import ru.otus.homework.domain.abbvie.Drug;
import ru.otus.homework.domain.abbvie.Pipeline;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbbvieReceiveServ implements IReceiveService {

    @Value("${update.abbvie.url}")
    String url;

    @Autowired
    private RestOperations rest;

    @HystrixCommand
    @Override
    public List<DrugDto> getData() throws RestClientException {
        Pipeline pipe = rest.getForObject(url, Pipeline.class);
        return pipe.getMolecules().stream().map(m -> convertToDto(m)).collect(Collectors.toList());
    }

    private DrugDto convertToDto(Drug drug) {
        DrugDto dto = new DrugDto();
        dto.setName(drug.getBrandName());
        dto.setArea(drug.getTherapyArea());
        dto.setDescription(drug.getDescription());
        dto.setLink(drug.getPermalink());
        List<Asset> assets = drug.getAssets();
        if (!assets.isEmpty()) {
            dto.setPhase(trimPhase(assets.get(0).getPhase()));
        }
        return dto;
    }

    private String trimPhase(String phase) {
        return phase.replace("Phase ","");
    }
}


