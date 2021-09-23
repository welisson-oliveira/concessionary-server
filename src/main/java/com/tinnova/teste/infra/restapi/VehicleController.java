package com.tinnova.teste.infra.restapi;

import com.tinnova.teste.domain.Vehicle;
import com.tinnova.teste.domain.VehicleApplicationServices;
import com.tinnova.teste.infra.dto.VehicleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleApplicationServices vehicleApplicationServices;
    private final VehicleParser vehicleParser;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleDTO> findAll() {
        return vehicleParser.parseAllToDTO(vehicleApplicationServices.findAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleDTO findById(@PathVariable final Long id) {
        return vehicleParser.parseToDTO(vehicleApplicationServices.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleDTO create(@RequestBody final VehicleDTO vehicleDTO) {
        return this.vehicleParser.parseToDTO(this.vehicleApplicationServices.create(this.vehicleParser.parse(vehicleDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleDTO update(@PathVariable final Long id, @RequestBody final VehicleDTO vehicleDTO) {
        return this.vehicleParser.parseToDTO(this.vehicleApplicationServices.update(id, this.vehicleParser.parse(vehicleDTO)));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleDTO sold(@PathVariable final Long id) {
        return this.vehicleParser.parseToDTO(this.vehicleApplicationServices.sold(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id) {
        this.vehicleApplicationServices.delete(id);
    }

    @GetMapping("/not-sold")
    @ResponseStatus(HttpStatus.OK)
    public Integer getAllSold() {
        return this.vehicleApplicationServices.getNotSoldSize();
    }

    @GetMapping("/per-decade")
    @ResponseStatus(HttpStatus.OK)
    public Map<Integer, List<VehicleDTO>> getAllPerDecade() {
        Map<Integer, List<Vehicle>> map = this.vehicleApplicationServices.getAllPerDecade();
        Map<Integer, List<VehicleDTO>> mapDto = new HashMap<>();
        for(Integer key : map.keySet()){
            mapDto.put(key, map.get(key).stream().map(this.vehicleParser::parseToDTO).collect(Collectors.toList()));
        }

        return mapDto;
    }

    @GetMapping("/by-manufacturer")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<VehicleDTO>> getAllByManufacturer() {
        Map<String, List<Vehicle>> map = this.vehicleApplicationServices.getAllByManufacturer();
        Map<String, List<VehicleDTO>> mapDto = new HashMap<>();
        for(String key : map.keySet()){
            mapDto.put(key, map.get(key).stream().map(this.vehicleParser::parseToDTO).collect(Collectors.toList()));
        }

        return mapDto;
    }

    @GetMapping("/last-week")
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleDTO> getAllLastWeek() {
        return this.vehicleParser.parseAllToDTO(this.vehicleApplicationServices.getAllLastWeek());

    }



}
