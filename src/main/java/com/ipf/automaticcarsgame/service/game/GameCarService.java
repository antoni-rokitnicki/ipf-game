package com.ipf.automaticcarsgame.service.game;

import com.ipf.automaticcarsgame.domain.GameCar;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.game.GameCarRequest;
import com.ipf.automaticcarsgame.mapper.GameCarMapper;
import com.ipf.automaticcarsgame.repository.GameCarRepository;
import com.ipf.automaticcarsgame.validator.game.car.PutCarInGameValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameCarService {

    private final GameCarRepository gameCarRepository;
    private final PutCarInGameValidator putCarInGameValidator;
    private final GameCarMapper gameCarMapper;

    public GameCarService(GameCarRepository gameCarRepository, PutCarInGameValidator putCarInGameValidator, GameCarMapper gameCarMapper) {
        this.gameCarRepository = gameCarRepository;
        this.putCarInGameValidator = putCarInGameValidator;
        this.gameCarMapper = gameCarMapper;
    }

    @Transactional
    public Result addCarToGame(GameCarRequest gameCarRequest){
        Result result = putCarInGameValidator.validate(gameCarRequest);

        if (result.isValid()) {
            GameCar gameCar = gameCarMapper.map(gameCarRequest);
            gameCarRepository.save(gameCar);
        }

        // TODO save last move

        return result;
    }
}
