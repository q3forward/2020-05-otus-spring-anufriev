package ru.otus.homework.domain.bms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BmsDrug {
    private String compoundname;
    private String phaseTag;
    private String researcharea;
    private String category;
    private String subcategory;
    private Boolean repeat;
}
