package com.ipf.automaticcarsgame.validator.game;

import com.ipf.automaticcarsgame.domain.Game;
import com.ipf.automaticcarsgame.dto.game.GameRequest;
import com.ipf.automaticcarsgame.repository.GameRepository;
import com.ipf.automaticcarsgame.validator.ValidationResult;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class GameAlreadyExistValidator implements GameValidator{

    private final GameRepository gameRepository;

    public GameAlreadyExistValidator(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public ValidationResult validate(GameRequest gameRequest) {
        ValidationResult validationResult = new ValidationResult();

        if(!StringUtils.isEmpty(gameRequest.getRoadMapName())){
            Optional<Game> gameOpt = this.gameRepository.findActiveGameByRoadMapName(gameRequest.getRoadMapName());

            if (gameOpt.isPresent()) {
                ValidationResult.Error gameAlreadyExist = new ValidationResult.Error("GAME_ALREADY_EXIST", "Game on map: " + gameRequest.getRoadMapName() + " exists");
                validationResult.addError(gameAlreadyExist);
            }
        }

        return validationResult;

    }
}
