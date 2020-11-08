package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="drugs")
public class Drug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @Column(name="name")
    private String name;

    @Column(name="area")
    private String area;

    @Column(name="phase")
    private String phase;

    @Column(name="description")
    private String description;

    @Column(name="link")
    private String link;

    public Drug(String drugName) {
        this.name = name;
    }
}
