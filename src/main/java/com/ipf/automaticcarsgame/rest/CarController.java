package com.ipf.automaticcarsgame.rest;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.mapper.ResponseMapper;
import com.ipf.automaticcarsgame.service.car.CarService;
import com.ipf.automaticcarsgame.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/cars", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CarController {

    private static final Logger LOG = LoggerFactory.getLogger(CarController.class);

    private final CarService carService;

    CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    ResponseEntity<Object> createCar(@RequestBody CarRequest carRequest) {
        LOG.info("create car, request: {}", carRequest);

        Result result = carService.createCar(carRequest);
        return ResponseMapper.map(result);
    }

    @GetMapping
    ResponseEntity<Object> findAll() {
        LOG.info("find all cars");
        List<Car> cars = carService.findAll();

        return ResponseEntity.ok(cars);
    }

    @DeleteMapping
    ResponseEntity<Object> removeCar(@RequestBody CarRequest carRequest) {
        LOG.info("remove car, request: {}", carRequest);

        Result result = carService.removeCar(carRequest);

        return ResponseMapper.map(result);
    }
}
