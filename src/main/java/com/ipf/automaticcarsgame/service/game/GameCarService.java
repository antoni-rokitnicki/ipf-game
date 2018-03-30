package com.ipf.automaticcarsgame.service.game;

import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.game.GameCarRequest;
import com.ipf.automaticcarsgame.repository.GameCarRepository;
import com.ipf.automaticcarsgame.validator.game.car.PutCarInGameValidator;
import org.springframework.stereotype.Service;

@Service
public class GameCarService {

    private final GameCarRepository gameCarRepository;
    private final PutCarInGameValidator putCarInGameValidator;

    public GameCarService(GameCarRepository gameCarRepository, PutCarInGameValidator putCarInGameValidator) {
        this.gameCarRepository = gameCarRepository;
        this.putCarInGameValidator = putCarInGameValidator;
    }

    public Result addCarToGame(GameCarRequest gameCarRequest) {
        Result result = putCarInGameValidator.validate(gameCarRequest);
        return result;
    }

}
