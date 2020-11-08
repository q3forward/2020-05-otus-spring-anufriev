package ru.otus.homework.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import ru.otus.homework.domain.Drug;

import java.util.List;

@FeignClient(name = "updater")
public interface UpdaterProxy {

    @GetMapping(value = "/api/update")
    List<Drug> getExternalData(@RequestParam("company") String company) throws RestClientException;
}