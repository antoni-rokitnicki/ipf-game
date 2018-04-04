package com.ipf.automaticcarsgame.validator.game.movement;

import com.ipf.automaticcarsgame.domain.GameCar;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.repository.GameCarRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReturnCarValidator {
    private final GameCarRepository gameCarRepository;

    public ReturnCarValidator(GameCarRepository gameCarRepository) {
        this.gameCarRepository = gameCarRepository;
    }

    public Result validate(String carName, int noOfMovements) {
        final Optional<GameCar> activeGameCar = this.gameCarRepository.findGameCarByCarNameAndActiveGame(carName);
        if (activeGameCar.isPresent()) {
            if (activeGameCar.get().isDeleted()) {
                return Result.ResultBuilder.builder().addError(new Result.Error("CAR_NOT_IN_GAME", "Car not in game")).build();
            }
        } else {
            return Result.ResultBuilder.builder().addError(new Result.Error("NOT_ACTIVE_GAME", "Not active game")).build();
        }
        if (activeGameCar.get().getCar().isCrashed()) {
            return Result.ResultBuilder.builder().addError(new Result.Error("CAR_IS_CRASHED", "Car is crashed")).build();
        }
        if (noOfMovements < 1) {
            return Result.ResultBuilder.builder().addError(new Result.Error("INVALID_NO_OF_MOVEMENTS", "invalid no of movements")).build();
        }
        return Result.ResultBuilder.builder().build();
    }


}
