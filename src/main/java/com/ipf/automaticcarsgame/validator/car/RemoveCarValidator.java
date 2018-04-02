package com.ipf.automaticcarsgame.validator.car;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.domain.GameCar;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.repository.CarRepository;
import com.ipf.automaticcarsgame.repository.GameCarRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class RemoveCarValidator implements CarValidator{

    private final CarRepository carRepository;
    private final GameCarRepository gameCarRepository;

    public RemoveCarValidator(CarRepository carRepository, GameCarRepository gameCarRepository) {
        this.carRepository = carRepository;
        this.gameCarRepository = gameCarRepository;
    }

    @Override
    public Result validate(CarRequest carRequest) {
        Result result = new Result();

        if (StringUtils.isEmpty(carRequest.getName())) {
            result.addError(new Result.Error("Car's name cannot be null or empty"));
        }else{
            Optional<Car> carOpt = carRepository.findByName(carRequest.getName());
            if (!carOpt.isPresent()) {
                result.addError(new Result.Error("This car doesn't exist"));
            }
        }

        if(result.isValid()){
            Optional<GameCar> gameCarOpt = gameCarRepository.checkIfCarWasUsed(carRequest.getName());
            if (gameCarOpt.isPresent()) {
                result.addError(new Result.Error("Cannot remove car because this car already has used"));
            }
        }

        return result;
    }
}
