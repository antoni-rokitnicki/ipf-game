package com.ipf.automaticcarsgame.service.car;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.dto.car.CarResponse;
import com.ipf.automaticcarsgame.mapper.CarMapper;
import com.ipf.automaticcarsgame.repository.CarRepository;
import com.ipf.automaticcarsgame.validator.car.CarAlreadyExistsValidator;
import com.ipf.automaticcarsgame.validator.car.CarRequestValidator;
import com.ipf.automaticcarsgame.validator.car.RemoveCarValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ipf.automaticcarsgame.dto.Result.ResultBuilder;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    private final CarAlreadyExistsValidator carAlreadyExistsValidator;
    private final CarRequestValidator carRequestValidator;
    private final RemoveCarValidator removeCarValidator;

    public CarService(CarRepository carRepository,
                      CarMapper carMapper,
                      CarAlreadyExistsValidator carAlreadyExistsValidator,
                      CarRequestValidator carRequestValidator, RemoveCarValidator removeCarValidator) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
        this.carAlreadyExistsValidator = carAlreadyExistsValidator;
        this.carRequestValidator = carRequestValidator;
        this.removeCarValidator = removeCarValidator;
    }

    @Transactional
    public Result createCar(CarRequest carRequest) {
        Result result = validateCar(carRequest);

        if (result.isValid()) {
            Car car = carMapper.map(carRequest);
            carRepository.save(car);
        }

        return result;
    }

    @Transactional
    public List<CarResponse> findAll() {
        Iterable<Car> cars = this.carRepository.findAll();
        return carMapper.map(cars);
    }

    @Transactional
    public Result removeCar(String carName) {
        CarRequest carRequest = new CarRequest(carName);
        Result result = removeCarValidator.validate(carRequest);

        if (result.isValid()) {


            Optional<Car> carOpt = carRepository.findByName(carRequest.getName());
            carOpt.ifPresent(carRepository::delete);
        }

        return result;
    }


    @Transactional
    public Result repairCar(String carName) {
        final Optional<Car> carOpt = this.carRepository.findByName(carName);
        if (carOpt.isPresent()) {
            carOpt.get().setCrashed(false);
            return ResultBuilder.builder().build();
        } else {
            return ResultBuilder.builder().addError(createError()).build();
        }
    }

    private Result.Error createError() {
        return new Result.Error("DOES_NOT_EXIST", "Car does not exist");
    }

    private Result validateCar(CarRequest carRequest) {
        Result result = carRequestValidator.validate(carRequest);

        if (result.isValid()) {
            result = carAlreadyExistsValidator.validate(carRequest);
        }

        return result;
    }

}
