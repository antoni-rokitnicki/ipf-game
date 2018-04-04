package com.ipf.automaticcarsgame.service.game;

import com.ipf.automaticcarsgame.domain.Game;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.game.GameRequest;
import com.ipf.automaticcarsgame.mapper.GameMapper;
import com.ipf.automaticcarsgame.repository.GameRepository;
import com.ipf.automaticcarsgame.validator.game.GameAlreadyExistValidator;
import com.ipf.automaticcarsgame.validator.game.GameRequestValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameRequestValidator gameRequestValidator;
    private final GameAlreadyExistValidator gameAlreadyExistValidator;
    private final GameMapper gameMapper;
    private final Integer idlenessTimeSecond;

    public GameService(GameRepository gameRepository,
                       GameRequestValidator gameRequestValidator,
                       GameAlreadyExistValidator gameAlreadyExistValidator,
                       GameMapper gameMapper, @Value("${game.idleness.timeSecond}") Integer idlenessTimeSecond) {
        this.gameRepository = gameRepository;
        this.gameRequestValidator = gameRequestValidator;
        this.gameAlreadyExistValidator = gameAlreadyExistValidator;
        this.gameMapper = gameMapper;
        this.idlenessTimeSecond = idlenessTimeSecond;
    }

    @Transactional
    public Result createGame(GameRequest gameRequest) {
        Result result = validate(gameRequest);

        if (result.isValid()) {
            Game game = gameMapper.map(gameRequest);
            game.setFinishDate(Date.from(ZonedDateTime.now().plusSeconds(idlenessTimeSecond).toInstant()));
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


}
