package ru.otus.homework.domain.abbvie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Drug {
    private String id;
    private String permalink;
    private String brandName;
    private String therapyArea;
    private String therapyAreaLabel;
    private String type;
    private String typeLabel;
    private String description;
    private List<Asset> assets;
}
