package com.ipf.automaticcarsgame.rest;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.service.car.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ipf.automaticcarsgame.mapper.ResponseEntityMapper.mapToResponseEntity;


@RestController
@RequestMapping(value = "/api/cars", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CarController {

    private static final Logger LOG = LoggerFactory.getLogger(CarController.class);

    private final CarService carService;

    CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    ResponseEntity<Response<Void>> createCar(@RequestBody CarRequest carRequest) {
        LOG.info("create car, request: {}", carRequest);

        Result result = carService.createCar(carRequest);
        return mapToResponseEntity(result);
    }

    @GetMapping
    ResponseEntity<Response<List<Car>>> findAll() {
        LOG.info("find all cars");
        List<Car> cars = carService.findAll();
        return mapToResponseEntity(cars);
    }

    @DeleteMapping
    ResponseEntity<Response<Void>> removeCar(@RequestBody CarRequest carRequest) {
        LOG.info("remove car, request: {}", carRequest);

        Result result = carService.removeCar(carRequest);

        return mapToResponseEntity(result);
    }

    //todo check mapping
    @PutMapping
    ResponseEntity<Response<Void>> repairCar(@RequestBody CarRequest carRequest) {
        LOG.info("repair car car, request: {}", carRequest);

        final Result result = carService.repairCar(carRequest);

        return mapToResponseEntity(result);
    }


}
