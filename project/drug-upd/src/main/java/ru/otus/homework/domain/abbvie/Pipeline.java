package ru.otus.homework.domain.abbvie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pipeline {
    private List<Drug> molecules;
}
