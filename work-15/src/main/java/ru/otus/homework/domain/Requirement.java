package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Requirement {
    private final String content;
    private String analyticDescription;

    public Requirement(String content) {
        this.content = content;
    }
}
