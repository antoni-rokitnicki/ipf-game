package com.ipf.automaticcarsgame.rest;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/cars", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CarController {

    private static final Logger LOG = LoggerFactory.getLogger(CarController.class);

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public Response<CarRequest> createCar(@RequestBody CarRequest carRequest) {
        LOG.info("create car, request: {}", carRequest);
        return carService.createCar(carRequest);
    }


    @GetMapping
    public Response<List<Car>> findAll() {
        LOG.info("find all cars");
        List<Car> cars = carService.findAll();
        return new Response<>(cars);
    }

    @DeleteMapping
    public Response<Void> removeCar(@RequestBody CarRequest carRequest) {
        LOG.info("remove car, request: {}", carRequest);
        return new Response<>();
    }

}
