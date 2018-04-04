package com.ipf.automaticcarsgame.service.game;

import com.ipf.automaticcarsgame.domain.GameCar;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.game.GameCarRequest;
import com.ipf.automaticcarsgame.dto.game.RemoveGameCarRequest;
import com.ipf.automaticcarsgame.mapper.GameCarMapper;
import com.ipf.automaticcarsgame.repository.GameCarRepository;
import com.ipf.automaticcarsgame.validator.game.car.PutCarInGameValidator;
import com.ipf.automaticcarsgame.validator.game.car.RemoveCarFromGameValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GameCarService {

    private final GameCarRepository gameCarRepository;
    private final GameCarMapper gameCarMapper;
    private final PutCarInGameValidator putCarInGameValidator;
    private final RemoveCarFromGameValidator removeCarFromGameValidator;


    public GameCarService(GameCarRepository gameCarRepository, PutCarInGameValidator putCarInGameValidator, GameCarMapper gameCarMapper, RemoveCarFromGameValidator removeCarFromGameValidator) {
        this.gameCarRepository = gameCarRepository;
        this.putCarInGameValidator = putCarInGameValidator;
        this.gameCarMapper = gameCarMapper;
        this.removeCarFromGameValidator = removeCarFromGameValidator;
    }

    @Transactional
    public Result addCarToGame(GameCarRequest gameCarRequest) {
        Result result = putCarInGameValidator.validate(gameCarRequest);

        if (result.isValid()) {
            GameCar gameCar = gameCarMapper.map(gameCarRequest);
            gameCarRepository.save(gameCar);
        }
        return result;
    }

    @Transactional
    public Result removeCarFromGame(RemoveGameCarRequest removeGameCarRequest) {
        Result result = removeCarFromGameValidator.validate(removeGameCarRequest.getGameCarRequest());

        if (result.isValid()) {
            Optional<GameCar> activeGameByCar = gameCarRepository.findGameCarByCarNameAndActiveGame(removeGameCarRequest.getCar());
            activeGameByCar.ifPresent(gameCarRepository::delete);
        }

        return result;
    }
}
