package com.tinnova.teste.domain;

import com.tinnova.teste.infra.dto.VehicleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleApplicationServices {

    private final VehicleRepository vehicleRepository;

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public Vehicle findById(final Long id) {
        return this.vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not Found"));
    }

    @Transactional
    public Vehicle create(final Vehicle vehicle) {
        vehicle.create();
        return this.vehicleRepository.save(vehicle);
    }

    @Transactional
    public Vehicle update(final Long id, final Vehicle vehicle) {
        final Vehicle vehicleToUpdate = this.findById(id);
        vehicleToUpdate.update(vehicle);
        return this.vehicleRepository.save(vehicleToUpdate);
    }

    @Transactional
    public Vehicle sold(final Long id) {
        final Vehicle vehicleToUpdate = this.findById(id);
        vehicleToUpdate.sold();
        return vehicleToUpdate;
    }

    @Transactional
    public void delete(final Long id) {
        this.vehicleRepository.deleteById(id);
    }

    public Map<Integer, List<Vehicle>> getAllPerDecade() {
        return this.vehicleRepository.findAll().stream()
                .collect(Collectors.groupingBy(Vehicle::getDecade));
    }

    public Map<String, List<Vehicle>> getAllByManufacturer() {
        return this.vehicleRepository.findAll().stream()
                .collect(Collectors.groupingBy(Vehicle::getBrand));
    }

    public List<Vehicle> getAllLastWeek() {
        LocalDate lastWeek = LocalDate.now().minusDays(7);
        return this.vehicleRepository.findAllByCreatedGreaterThan(lastWeek);
    }

    public Integer getNotSoldSize() {
        return this.vehicleRepository.findAllBySold(false).size();
    }
}
