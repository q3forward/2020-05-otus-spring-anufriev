package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.Collection;

/**
 * КОНЦЕПЦИЯ. Состоит из требований с пояснениями аналитика
 */

@AllArgsConstructor
@ToString
public class Concept {
    private final Collection<Requirement> describedReqs;
}
