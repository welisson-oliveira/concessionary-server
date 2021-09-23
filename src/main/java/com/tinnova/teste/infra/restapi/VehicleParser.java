package com.tinnova.teste.infra.restapi;

import com.tinnova.teste.domain.Vehicle;
import com.tinnova.teste.infra.dto.VehicleDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehicleParser {
    public List<VehicleDTO> parseAllToDTO(final List<Vehicle> vehicles) {
        return vehicles.stream().map(this::parseToDTO).collect(Collectors.toList());
    }

    public VehicleDTO parseToDTO(final Vehicle vehicle) {
        return new VehicleDTO(vehicle.getId(), vehicle.getName(), vehicle.getBrand(), vehicle.getYear(), vehicle.getDescription(), vehicle.isSold(), vehicle.getCreated(), vehicle.getUpdated());
    }

    public Vehicle parse(final VehicleDTO vehicle) {
        return new Vehicle(vehicle.getId(), vehicle.getName(), vehicle.getBrand(), vehicle.getYear(), vehicle.getDescription(), vehicle.isSold(), vehicle.getCreated(), vehicle.getUpdated());
    }
}
