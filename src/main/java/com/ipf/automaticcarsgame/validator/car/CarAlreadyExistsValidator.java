package com.ipf.automaticcarsgame.validator.car;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.repository.CarRepository;
import com.ipf.automaticcarsgame.dto.Result;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CarAlreadyExistsValidator implements CarValidator{

    private final CarRepository carRepository;

    public CarAlreadyExistsValidator(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Result validate(CarRequest carRequest) {
        Optional<Car> carOptional = carRepository.findByName(carRequest.getName());

        Result result = new Result();
        carOptional.ifPresent(car -> result.addError(new Result.Error("Car " + carRequest.getName() + " already exists")));

        return result;
    }
}
