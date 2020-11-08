package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="brief")
    private String brief;

    @Column(name="country")
    private String country;

    @Column(name="year")
    private Integer year;

    @Column(name="link")
    private String link;

    public Company(String name) {
        this.name = name;
    }
}
