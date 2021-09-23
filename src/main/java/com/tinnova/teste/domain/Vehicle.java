package com.tinnova.teste.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vehicle")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_seq_gen")
    @SequenceGenerator(name = "vehicle_seq_gen", sequenceName = "vehicle_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "vehicle_name")
    private String name;

    private String brand;

    private Integer year;

    private String description;

    private boolean sold;

    @Type(type = "java.time.LocalDate")
    private LocalDate created;

    @Type(type = "java.time.LocalDate")
    private LocalDate updated;

    public void create() {
        this.sold = false;
        this.created = LocalDate.now();
    }

    public void update(final Vehicle vehicle) {
        this.name = vehicle.getName();
        this.brand = vehicle.getBrand();
        this.year = vehicle.getYear();
        this.description = vehicle.getDescription();
        this.sold = vehicle.isSold();
        this.updated = LocalDate.now();
    }

    public void sold() {
        this.updated = LocalDate.now();
        this.sold = true;
    }

    public Integer getDecade() {
        return (this.year / 10) * 10;
    }
}
