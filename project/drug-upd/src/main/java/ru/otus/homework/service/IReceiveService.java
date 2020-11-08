package ru.otus.homework.service;

import org.springframework.web.client.RestClientException;
import ru.otus.homework.domain.DrugDto;

import java.util.List;

public interface IReceiveService {
    List<DrugDto> getData() throws RestClientException;
}
