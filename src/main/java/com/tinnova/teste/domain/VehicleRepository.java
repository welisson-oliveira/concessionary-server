package com.tinnova.teste.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findAllBySold(final boolean sold);

    List<Vehicle> findAllByCreatedGreaterThan(LocalDate lastWeek);
}
