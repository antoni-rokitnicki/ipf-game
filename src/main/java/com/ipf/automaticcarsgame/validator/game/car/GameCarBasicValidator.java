package com.ipf.automaticcarsgame.validator.game.car;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.game.GameCarRequest;
import com.ipf.automaticcarsgame.repository.CarRepository;
import com.ipf.automaticcarsgame.repository.RoadmapRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class GameCarBasicValidator implements GameCarValidator {

    private final RoadmapRepository roadmapRepository;
    private final CarRepository carRepository;

    public GameCarBasicValidator(RoadmapRepository roadmapRepository, CarRepository carRepository) {
        this.roadmapRepository = roadmapRepository;
        this.carRepository = carRepository;
    }

    @Override
    public Result validate(GameCarRequest gameCarRequest) {
        Result result = new Result();

        validateCar(gameCarRequest, result);
        validateMap(gameCarRequest, result);

        return result;
    }

    private void validateMap(GameCarRequest gameCarRequest, Result result) {
        if (StringUtils.isEmpty(gameCarRequest.getRoadmap())) {
            result.addError(new Result.Error("MAP_EMPTY", "RoadMap name cannot be null"));
        } else {
            Optional<Roadmap> roadmapOpt = roadmapRepository.findByNameIgnoreCaseAndDeleted(gameCarRequest.getRoadmap(), false);
            if (!roadmapOpt.isPresent()) {
                result.addError(new Result.Error("MAP_NOT_EXIST", "Roadmap " + gameCarRequest.getRoadmap() + " doesn't exist"));
            }
        }
    }

    private void validateCar(GameCarRequest gameCarRequest, Result result) {
        if (StringUtils.isEmpty(gameCarRequest.getCar())) {
            result.addError(new Result.Error("CAR_EMPTY", "Car's name cannot be null or empty"));
        } else {
            Optional<Car> carOpt = carRepository.findByName(gameCarRequest.getCar());
            if (carOpt.isPresent()) {
                result.addError(new Result.Error("CAR_NOT_EXIST", "Car " + gameCarRequest.getCar() + " doesn't exist"));
            }
        }
    }
}
