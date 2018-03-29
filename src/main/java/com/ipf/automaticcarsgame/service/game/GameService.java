package com.ipf.automaticcarsgame.service.game;

import com.ipf.automaticcarsgame.domain.Game;
import com.ipf.automaticcarsgame.dto.game.GameRequest;
import com.ipf.automaticcarsgame.mapper.GameMapper;
import com.ipf.automaticcarsgame.repository.GameRepository;
import com.ipf.automaticcarsgame.validator.Result;
import com.ipf.automaticcarsgame.validator.game.GameAlreadyExistValidator;
import com.ipf.automaticcarsgame.validator.game.GameRequestValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameRequestValidator gameRequestValidator;
    private final GameAlreadyExistValidator gameAlreadyExistValidator;
    private final GameMapper gameMapper;


    public GameService(GameRepository gameRepository,
                       GameRequestValidator gameRequestValidator,
                       GameAlreadyExistValidator gameAlreadyExistValidator,
                       GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameRequestValidator = gameRequestValidator;
        this.gameAlreadyExistValidator = gameAlreadyExistValidator;
        this.gameMapper = gameMapper;
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

    public Result validate(GameRequest gameRequest){
        Result result = gameRequestValidator.validate(gameRequest);

        if (result.isValid()) {
            result = gameAlreadyExistValidator.validate(gameRequest);
        }

        return result;
    }
}
