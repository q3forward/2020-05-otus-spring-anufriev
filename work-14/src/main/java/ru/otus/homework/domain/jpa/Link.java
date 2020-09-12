package ru.otus.homework.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="link_ids")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="mongoid")
    String mongoId;

    @Column(name="jpaid")
    Long jpaId;

    @Column(name="class")
    String className;

    public Link(String mongoId, String className) {
        this.mongoId = mongoId;
        this.className = className;
    }
}
