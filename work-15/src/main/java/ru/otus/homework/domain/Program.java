package ru.otus.homework.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Program {
    private Concept concept;
    private String code;
    private Boolean testResult;
    private Manual manual;

    public Program(String code) {
        this.code = code;
    }
}
