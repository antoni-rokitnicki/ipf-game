package com.ipf.automaticcarsgame.service.car;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.mapper.CarMapper;
import com.ipf.automaticcarsgame.repository.CarRepository;
import com.ipf.automaticcarsgame.validator.ValidationResult;
import com.ipf.automaticcarsgame.validator.car.CarAlreadyExistsValidator;
import com.ipf.automaticcarsgame.validator.car.CarRequestValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    private final CarAlreadyExistsValidator carAlreadyExistsValidator;
    private final CarRequestValidator carRequestValidator;

    public CarService(CarRepository carRepository,
                      CarMapper carMapper,
                      CarAlreadyExistsValidator carAlreadyExistsValidator,
                      CarRequestValidator carRequestValidator) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
        this.carAlreadyExistsValidator = carAlreadyExistsValidator;
        this.carRequestValidator = carRequestValidator;
    }

    @Transactional
    public ValidationResult createCar(CarRequest carRequest) {
        ValidationResult validationResult = validateCar(carRequest);

        if (validationResult.isValid()) {
            Car car = carMapper.map(carRequest);
            carRepository.save(car);
        }

        return validationResult;
    }

    @Transactional
    public List<Car> findAll() {
        return (List<Car>) this.carRepository.findAll();
    }

    @Transactional
    public ValidationResult removeCar(CarRequest carRequest) {
        ValidationResult validationResult = carRequestValidator.validate(carRequest);

        if (validationResult.isValid()) {
            Optional<Car> carOpt = carRepository.findByName(carRequest.getName());
            carOpt.ifPresent(carRepository::delete);
        }

        return validationResult;
    }

    private ValidationResult validateCar(CarRequest carRequest) {
        ValidationResult validationResult = carRequestValidator.validate(carRequest);

        if (validationResult.isValid()) {
            validationResult = carAlreadyExistsValidator.validate(carRequest);
        }

        return validationResult;
    }
}
