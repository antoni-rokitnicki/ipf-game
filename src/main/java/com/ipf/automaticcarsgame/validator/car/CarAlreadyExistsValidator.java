package com.ipf.automaticcarsgame.validator.car;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.repository.CarRepository;
import com.ipf.automaticcarsgame.validator.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CarAlreadyExistsValidator implements CarValidator{

    private final CarRepository carRepository;

    public CarAlreadyExistsValidator(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public ValidationResult validate(CarRequest carRequest) {
        Optional<Car> carOptional = carRepository.findByName(carRequest.getName());

        ValidationResult validationResult = new ValidationResult();
        carOptional.ifPresent(car -> validationResult.addError(new ValidationResult.Error("Car " + carRequest.getName() + " already exists")));

        return validationResult;
    }
}
