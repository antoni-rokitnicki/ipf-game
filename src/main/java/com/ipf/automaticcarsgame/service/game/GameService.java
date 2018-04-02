package com.ipf.automaticcarsgame.service.game;

import com.ipf.automaticcarsgame.domain.Game;
import com.ipf.automaticcarsgame.domain.Movement;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.game.GameRequest;
import com.ipf.automaticcarsgame.mapper.GameMapper;
import com.ipf.automaticcarsgame.repository.GameRepository;
import com.ipf.automaticcarsgame.repository.MovementRepository;
import com.ipf.automaticcarsgame.validator.game.GameAlreadyExistValidator;
import com.ipf.automaticcarsgame.validator.game.GameRequestValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameRequestValidator gameRequestValidator;
    private final GameAlreadyExistValidator gameAlreadyExistValidator;
    private final GameMapper gameMapper;
    private final MovementRepository movementRepository;


    public GameService(GameRepository gameRepository,
                       GameRequestValidator gameRequestValidator,
                       GameAlreadyExistValidator gameAlreadyExistValidator,
                       GameMapper gameMapper, MovementRepository movementRepository) {
        this.gameRepository = gameRepository;
        this.gameRequestValidator = gameRequestValidator;
        this.gameAlreadyExistValidator = gameAlreadyExistValidator;
        this.gameMapper = gameMapper;
        this.movementRepository = movementRepository;
    }

    @Transactional
    public Result createGame(GameRequest gameRequest) {
        Result result = validate(gameRequest);

        if (result.isValid()) {
            Game game = gameMapper.map(gameRequest);
            gameRepository.save(game);
        }

        return result;
    }

    public Result validate(GameRequest gameRequest) {
        Result result = gameRequestValidator.validate(gameRequest);

        if (result.isValid()) {
            result = gameAlreadyExistValidator.validate(gameRequest);
        }

        return result;
    }

    public Optional<Game> getActiveGameByMapName(String mapName) {
        return this.gameRepository.findActiveGameByRoadMapName(mapName);
    }

    public Optional<Game> returnCar(Integer gameId, String carName, int noOfMovements) {
        final List<Movement> movements = this.movementRepository.findMovements(gameId, carName, new Date());
        final List<Movement> movments = movements.subList(0, noOfMovements);

        return null;
    }



}
