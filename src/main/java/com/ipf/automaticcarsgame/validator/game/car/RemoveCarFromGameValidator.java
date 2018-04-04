package com.ipf.automaticcarsgame.validator.game.car;

import com.ipf.automaticcarsgame.domain.GameCar;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.game.GameCarRequest;
import com.ipf.automaticcarsgame.repository.GameCarRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RemoveCarFromGameValidator implements GameCarValidator {

    private final GameCarBasicValidator gameCarBasicValidator;
    private final GameCarRepository gameCarRepository;

    public RemoveCarFromGameValidator(GameCarBasicValidator gameCarBasicValidator, GameCarRepository gameCarRepository) {
        this.gameCarBasicValidator = gameCarBasicValidator;
        this.gameCarRepository = gameCarRepository;
    }

    @Override
    public Result validate(GameCarRequest gameCarRequest) {
        Result result = gameCarBasicValidator.validate(gameCarRequest);

        Optional<GameCar> activeGameByCar = gameCarRepository.findGameCarByCarNameAndActiveGame(gameCarRequest.getCar());

        if (!activeGameByCar.isPresent()) {
            result.addError(new Result.Error("Cannot remove car from game because car isn't in a game"));
        }

        return result;
    }

}
