package com.tinnova.teste.infra.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VehicleDTO {

    private Long id;
    private String name;
    private String brand;
    private Integer year;
    private String description;
    private boolean sold;
    private LocalDate created;
    private LocalDate updated;
}
