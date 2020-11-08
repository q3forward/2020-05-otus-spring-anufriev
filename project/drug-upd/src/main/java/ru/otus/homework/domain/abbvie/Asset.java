package ru.otus.homework.domain.abbvie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Asset {
    private String id;
    private String indication;
    private String phase;
    private String type;
    private String description;
    private String phaseLabel;
}
