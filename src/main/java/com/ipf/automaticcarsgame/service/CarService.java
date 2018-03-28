package com.ipf.automaticcarsgame.service;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.mapper.CarMapper;
import com.ipf.automaticcarsgame.repository.CarRepository;
import com.ipf.automaticcarsgame.validator.ValidationResult;
import com.ipf.automaticcarsgame.validator.car.CreateCarRequestValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final CreateCarRequestValidator createCarRequestValidator;


    public CarService(CarRepository carRepository, CarMapper carMapper, CreateCarRequestValidator createCarRequestValidator) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
        this.createCarRequestValidator = createCarRequestValidator;
    }

    @Transactional
    public ValidationResult createCar(CarRequest carRequest) {
        ValidationResult validationResult = createCarRequestValidator.validate(carRequest);

        if(validationResult.isValid()){
            Car car = carMapper.map(carRequest);
            carRepository.save(car);
        }

        return validationResult;
    }

    public List<Car> findAll() {
        return (List<Car>) this.carRepository.findAll();
    }
}
