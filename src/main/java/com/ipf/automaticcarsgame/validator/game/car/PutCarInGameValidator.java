package com.ipf.automaticcarsgame.validator.game.car;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.domain.Position;
import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.dto.game.GameCarRequest;
import com.ipf.automaticcarsgame.repository.CarRepository;
import com.ipf.automaticcarsgame.repository.GameCarRepository;
import com.ipf.automaticcarsgame.repository.RoadmapRepository;
import com.ipf.automaticcarsgame.validator.ValidationResult;

import static com.ipf.automaticcarsgame.validator.ValidationResult.Error;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class PutCarInGameValidator implements GameCarValidator {

    private final GameCarRepository gameCarRepository;
    private final CarRepository carRepository;
    private final RoadmapRepository roadmapRepository;

    public PutCarInGameValidator(GameCarRepository gameCarRepository, CarRepository carRepository, RoadmapRepository roadmapRepository) {
        this.gameCarRepository = gameCarRepository;
        this.carRepository = carRepository;
        this.roadmapRepository = roadmapRepository;
    }

    @Override
    public ValidationResult validate(GameCarRequest gameCarRequest) {
        ValidationResult result = new ValidationResult();

        validateCar(gameCarRequest, result);
        validateMap(gameCarRequest, result);

        if (result.isValid()) {
            Position position = gameCarRequest.getPosition();
            if (position != null) {
                if (position.getCol() != null && position.getRow() != null) {
                    Optional<Roadmap> roadmapOpt = roadmapRepository.findByNameIgnoreCaseAndDeleted(gameCarRequest.getRoadmap(), false);

                }
            }
        }

        return null;
    }

    private void validateMap(GameCarRequest gameCarRequest, ValidationResult result) {
        if (StringUtils.isEmpty(gameCarRequest.getRoadmap())) {
            result.addError(new Error("MAP_EMPTY", "RoadMap name cannot be null"));
        } else {
            Optional<Roadmap> roadmapOpt = roadmapRepository.findByNameIgnoreCaseAndDeleted(gameCarRequest.getRoadmap(), false);
            if (!roadmapOpt.isPresent()) {
                result.addError(new Error("MAP_NOT_EXIST", "Roadmap " + gameCarRequest.getRoadmap() + " doesn't exist"));
            }
        }
    }

    private void validateCar(GameCarRequest gameCarRequest, ValidationResult result) {
        if (StringUtils.isEmpty(gameCarRequest.getCar())) {
            result.addError(new Error("CAR_EMPTY", "Car's name cannot be null or empty"));
        } else {
            Optional<Car> carOpt = carRepository.findByName(gameCarRequest.getCar());
            if (carOpt.isPresent()) {
                result.addError(new Error("CAR_NOT_EXIST", "Car " + gameCarRequest.getCar() + " doesn't exist"));
            }
        }
    }
}
