package com.ipf.automaticcarsgame.validator.game.car;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.domain.Position;
import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.game.GameCarRequest;
import com.ipf.automaticcarsgame.repository.CarRepository;
import com.ipf.automaticcarsgame.repository.GameCarRepository;
import com.ipf.automaticcarsgame.repository.RoadmapRepository;
import com.ipf.automaticcarsgame.service.roadmap.RoadmapPositionService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static com.ipf.automaticcarsgame.dto.Result.Error;

@Component
public class PutCarInGameValidator implements GameCarValidator {

    private final GameCarRepository gameCarRepository;
    private final CarRepository carRepository;
    private final RoadmapRepository roadmapRepository;

    private final RoadmapPositionService roadmapPositionService;

    public PutCarInGameValidator(GameCarRepository gameCarRepository, CarRepository carRepository, RoadmapRepository roadmapRepository, RoadmapPositionService roadmapPositionService) {
        this.gameCarRepository = gameCarRepository;
        this.carRepository = carRepository;
        this.roadmapRepository = roadmapRepository;
        this.roadmapPositionService = roadmapPositionService;
    }

    @Override
    public Result validate(GameCarRequest gameCarRequest) {
        Result result = new Result();

        validateCar(gameCarRequest, result);
        validateMap(gameCarRequest, result);
        validatePosition(gameCarRequest, result);

        return result;
    }

    private void validatePosition(GameCarRequest gameCarRequest, Result result) {
        if (result.isValid()) {
            Position position = gameCarRequest.getPosition();
            if (position != null && position.getCol() != null && position.getRow() != null) {
                validateCcnstraints(gameCarRequest, result);
            } else {
                result.addError(new Error("POSITION_INCORRECT", "Position is incorrect"));
            }
        }
    }

    private void validateCcnstraints(GameCarRequest gameCarRequest, Result result) {
        Optional<Roadmap> roadmapOpt = roadmapRepository.findByNameIgnoreCaseAndDeleted(gameCarRequest.getRoadmap(), false);

        if (roadmapOpt.isPresent()) {
            Roadmap roadmap = roadmapOpt.get();
            boolean fieldIsCorrect = roadmapPositionService.checkIfFieldIsCorrect(roadmap, gameCarRequest.getPosition());

            if(!fieldIsCorrect){
                result.addError(new Error("FIELD_INVALID", "Pointed position is invalid"));
            }else{
                boolean fieldIsOccupied = roadmapPositionService.checkIfFieldIsOccupied(roadmap, gameCarRequest.getPosition());

                if(!fieldIsOccupied){
                    result.addError(new Error("FIELD_OCCUPIED", "Pointed position is occupied"));
                }
            }
        }
    }

    private void validateMap(GameCarRequest gameCarRequest, Result result) {
        if (StringUtils.isEmpty(gameCarRequest.getRoadmap())) {
            result.addError(new Error("MAP_EMPTY", "RoadMap name cannot be null"));
        } else {
            Optional<Roadmap> roadmapOpt = roadmapRepository.findByNameIgnoreCaseAndDeleted(gameCarRequest.getRoadmap(), false);
            if (!roadmapOpt.isPresent()) {
                result.addError(new Error("MAP_NOT_EXIST", "Roadmap " + gameCarRequest.getRoadmap() + " doesn't exist"));
            }
        }
    }

    private void validateCar(GameCarRequest gameCarRequest, Result result) {
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
