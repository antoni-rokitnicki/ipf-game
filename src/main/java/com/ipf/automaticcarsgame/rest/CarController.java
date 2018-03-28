package com.ipf.automaticcarsgame.rest;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.service.CarService;
import com.ipf.automaticcarsgame.validator.ValidationResult;
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


    /**
     * Poprawić obiekty zwracane
     *
     * */

    @PostMapping
    ResponseEntity<ValidationResult> createCar(@RequestBody CarRequest carRequest) {
        LOG.info("create car, request: {}", carRequest);

        ResponseEntity<ValidationResult> responseEntity;

        ValidationResult validationResult = carService.createCar(carRequest);
        responseEntity = getValidationResultResponseEntity(validationResult);

        return responseEntity;
    }

    @GetMapping
    Response<List<Car>> findAll() {
        LOG.info("find all cars");
        List<Car> cars = carService.findAll();

        return new Response<>(cars);
    }

    @DeleteMapping
    ResponseEntity<ValidationResult> removeCar(@RequestBody CarRequest carRequest) {
        LOG.info("remove car, request: {}", carRequest);

        ValidationResult validationResult = carService.removeCar(carRequest);

        return getValidationResultResponseEntity(validationResult);
    }

    private ResponseEntity<ValidationResult> getValidationResultResponseEntity(ValidationResult validationResult) {
        ResponseEntity<ValidationResult> responseEntity;
        if (validationResult.isValid()) {
            responseEntity = ResponseEntity.ok(validationResult);
        } else {
            responseEntity = ResponseEntity.badRequest().body(validationResult);
        }
        return responseEntity;
    }
}
