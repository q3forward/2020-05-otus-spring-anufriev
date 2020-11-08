package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private Long id;
    private String name;
    private String brief;
    private String country;
    private Integer year;
    private String link;

    public Company(String name) {
        this.name = name;
    }
}
