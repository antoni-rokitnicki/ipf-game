package com.ipf.automaticcarsgame.mapper;

import com.ipf.automaticcarsgame.domain.*;
import com.ipf.automaticcarsgame.dto.game.GameCarRequest;
import com.ipf.automaticcarsgame.repository.CarRepository;
import com.ipf.automaticcarsgame.repository.GameRepository;
import com.ipf.automaticcarsgame.repository.RoadmapPositionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GameCarMapper {

    private final CarRepository carRepository;
    private final GameRepository gameRepository;
    private final RoadmapPositionRepository roadmapPositionRepository;

    public GameCarMapper(CarRepository carRepository, GameRepository gameRepository, RoadmapPositionRepository roadmapPositionRepository) {
        this.carRepository = carRepository;
        this.gameRepository = gameRepository;
        this.roadmapPositionRepository = roadmapPositionRepository;
    }

    public GameCar map(GameCarRequest gameCarRequest) {
        GameCar gameCar = new GameCar();

        Optional<Car> carOpt = carRepository.findByName(gameCarRequest.getCar());
        carOpt.ifPresent(car -> gameCar.setCarId(car.getId()));

        Optional<Game> gameOpt = gameRepository.findActiveGameByRoadMapName(gameCarRequest.getRoadmap());
        gameOpt.ifPresent(game -> gameCar.setGameId(game.getId()));

        Game game = gameOpt.get();
        Optional<RoadmapPosition> roadmapPositionOpt = roadmapPositionRepository.findByRoadmapAndPosition(game.getRoadmap(), gameCarRequest.getPosition());
        roadmapPositionOpt.ifPresent(roadmapPosition -> gameCar.setPositionId(roadmapPosition.getId()));

        return gameCar;
    }

}
