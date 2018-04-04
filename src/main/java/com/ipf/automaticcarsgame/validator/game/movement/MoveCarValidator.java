package com.ipf.automaticcarsgame.validator.game.movement;

import com.ipf.automaticcarsgame.domain.GameCar;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.car.CarType;
import com.ipf.automaticcarsgame.repository.GameCarRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MoveCarValidator {
    private static final String CAR_NOT_IN_GAME = "CAR_NOT_IN_GAME";
    private static final String NOT_ACTIVE_GAME = "NOT_ACTIVE_GAME";
    private static final String CAR_IS_CRASHED = "CAR_IS_CRASHED";
    private static final String INVALID_NR_OF_MOVEMENTS = "INVALID_NR_OF_MOVEMENTS";
    private static final String INVALID_NR_OF_MOVEMENTS_MESSAGE = "Invalid nr of movements";
    private static final String CAR_IS_CRASHED_MESSAGE = "Car is crashed";
    private static final String NOT_ACTIVE_GAME_MESSAGE = "Not active game";
    private static final String CAR_NOT_IN_GAME_MESSAGE = "Car not in game";
    private final GameCarRepository gameCarRepository;

    public MoveCarValidator(GameCarRepository gameCarRepository) {
        this.gameCarRepository = gameCarRepository;
    }

    public Result validate(String carName, Integer nrOfMovemetns) {
        final Optional<GameCar> activeGameCar = this.gameCarRepository.findGameCarByCarNameAndActiveGame(carName);
        if (activeGameCar.isPresent()) {
            if (activeGameCar.get().isDeleted()) {
                return Result.ResultBuilder.builder().addError(new Result.Error(CAR_NOT_IN_GAME, CAR_NOT_IN_GAME_MESSAGE)).build();
            }
        } else {
            return Result.ResultBuilder.builder().addError(new Result.Error(NOT_ACTIVE_GAME, NOT_ACTIVE_GAME_MESSAGE)).build();
        }
        if (activeGameCar.get().getCar().isCrashed()) {
            return Result.ResultBuilder.builder().addError(new Result.Error(CAR_IS_CRASHED, CAR_IS_CRASHED_MESSAGE)).build();
        }

        if (CarType.RACER.equals(activeGameCar.get().getCar().getType()) && (nrOfMovemetns > 2 || nrOfMovemetns < 1)) {
            return Result.ResultBuilder.builder().addError(new Result.Error(INVALID_NR_OF_MOVEMENTS, INVALID_NR_OF_MOVEMENTS_MESSAGE)).build();
        }
        if (!CarType.RACER.equals(activeGameCar.get().getCar().getType()) && (nrOfMovemetns != 1)) {
            return Result.ResultBuilder.builder().addError(new Result.Error(INVALID_NR_OF_MOVEMENTS, INVALID_NR_OF_MOVEMENTS_MESSAGE)).build();
        }

        return Result.ResultBuilder.builder().build();
    }

}
