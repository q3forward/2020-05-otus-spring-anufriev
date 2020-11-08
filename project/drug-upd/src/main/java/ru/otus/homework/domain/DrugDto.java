package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugDto {
    private String name;
    private String area;
    private String phase;
    private String description;
    private String link;

    public DrugDto(String drugName) {
        this.name = name;
    }
}
