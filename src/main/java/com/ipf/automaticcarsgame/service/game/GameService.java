package com.ipf.automaticcarsgame.service.game;

import com.ipf.automaticcarsgame.domain.*;
import com.ipf.automaticcarsgame.dto.MovementType;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.game.GameRequest;
import com.ipf.automaticcarsgame.mapper.GameMapper;
import com.ipf.automaticcarsgame.repository.GameCarRepository;
import com.ipf.automaticcarsgame.repository.GameRepository;
import com.ipf.automaticcarsgame.repository.MovementRepository;
import com.ipf.automaticcarsgame.service.roadmap.RoadmapPositionService;
import com.ipf.automaticcarsgame.validator.game.GameAlreadyExistValidator;
import com.ipf.automaticcarsgame.validator.game.GameRequestValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameRequestValidator gameRequestValidator;
    private final GameAlreadyExistValidator gameAlreadyExistValidator;
    private final GameMapper gameMapper;
    private final MovementRepository movementRepository;
    private final GameCarRepository gameCarRepository;
    private final RoadmapPositionService roadmapPositionService;


    public GameService(GameRepository gameRepository,
                       GameRequestValidator gameRequestValidator,
                       GameAlreadyExistValidator gameAlreadyExistValidator,
                       GameMapper gameMapper, MovementRepository movementRepository, GameCarRepository gameCarRepository, RoadmapPositionService roadmapPositionService) {
        this.gameRepository = gameRepository;
        this.gameRequestValidator = gameRequestValidator;
        this.gameAlreadyExistValidator = gameAlreadyExistValidator;
        this.gameMapper = gameMapper;
        this.movementRepository = movementRepository;
        this.gameCarRepository = gameCarRepository;
        this.roadmapPositionService = roadmapPositionService;
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

    public synchronized void moveCar(String carName, MovementType movementType, int nrOfMovemetns) {
        // todo validations
        final Optional<GameCar> gameCar = this.gameCarRepository.findGameCarByCarNameAndActiveGame(carName);
        final Integer positionId = gameCar.get().getPositionId();
        final Position currPosition = new Position();

        if (MovementType.LEFT.equals(movementType)) {
            final DirectionType currnetDirection = gameCar.get().getCurrnetDirection();
            gameCar.get().setCurrnetDirection(currnetDirection.nextLeftDirection());
        } else if (MovementType.RIGHT.equals(movementType)) {
            final DirectionType currnetDirection = gameCar.get().getCurrnetDirection();
            gameCar.get().setCurrnetDirection(currnetDirection.nextRightDirection());
        }else if(MovementType.FORWARD.equals(movementType)){
            final DirectionType currnetDirection = gameCar.get().getCurrnetDirection();

            final boolean isRoad = this.roadmapPositionService.checkIfFieldIsCorrect(gameCar.get().getGame().getRoadmap(), currPosition, currnetDirection, nrOfMovemetns);
            final boolean isOccupied = this.roadmapPositionService.checkIfFieldIsCorrect(gameCar.get().getGame().getRoadmap(), currPosition, currnetDirection, nrOfMovemetns);



            //gameCar.get().setPositionId();
        }


    }

    public Optional<Game> returnCar(Integer gameId, String carName, int noOfMovements) {
        final List<Movement> movements = this.movementRepository.findMovements(gameId, carName, new Date());
        final List<Movement> movments = movements.subList(0, noOfMovements);
        // todo

        return null;
    }


}
