package ru.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import ru.otus.homework.domain.DrugDto;
import ru.otus.homework.domain.bms.BmsDrug;
import ru.otus.homework.domain.bms.Pipeline;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BmsReceiveServ implements IReceiveService {

    @Value("${update.bms.url}")
    String url;

    @Autowired
    private RestOperations rest;

    @HystrixCommand
    @Override
    public List<DrugDto> getData() {
        Pipeline pipe = rest.getForObject(url, Pipeline.class);
        return pipe.getListings().stream().map(m -> convertToDto(m)).collect(Collectors.toList());
    }

    private DrugDto convertToDto(BmsDrug drug) {
        DrugDto dto = new DrugDto();
        dto.setName(getNameFrom(drug.getCompoundname()));
        dto.setArea(getAreaFrom(drug.getResearcharea()));
        dto.setLink(getLinkFrom(drug.getCompoundname()));
        dto.setPhase(getPhaseFrom(drug.getPhaseTag()));
        return dto;
    }

    private String getNameFrom(String fragment) {
        Document doc = Jsoup.parseBodyFragment(fragment);
        return doc.select("b").first().text();
    }

    private String getAreaFrom(String fragment) {
        Document doc = Jsoup.parseBodyFragment(fragment);
        return doc.select("p").text();
    }

    private String getLinkFrom(String fragment) {
        Document doc = Jsoup.parseBodyFragment(fragment);
        Elements aTag = doc.select("a");
        return aTag!=null && !aTag.isEmpty() ? aTag.first().attr("href") : "";
    }

    private String getPhaseFrom(String phaseTag) {
        return String.valueOf(phaseTag.charAt(phaseTag.length()-1));
    }


}


