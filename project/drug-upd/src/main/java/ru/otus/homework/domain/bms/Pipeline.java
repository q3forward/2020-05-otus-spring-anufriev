package ru.otus.homework.domain.bms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pipeline {
    private List<BmsDrug> listings;
}
