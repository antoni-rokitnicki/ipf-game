package com.ipf.automaticcarsgame.service.game;

import com.ipf.automaticcarsgame.domain.*;
import com.ipf.automaticcarsgame.dto.MovementType;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.game.GameRequest;
import com.ipf.automaticcarsgame.mapper.GameMapper;
import com.ipf.automaticcarsgame.repository.GameCarRepository;
import com.ipf.automaticcarsgame.repository.GameRepository;
import com.ipf.automaticcarsgame.repository.MovementRepository;
import com.ipf.automaticcarsgame.repository.RoadmapPositionRepository;
import com.ipf.automaticcarsgame.service.collision.CarCollisionService;
import com.ipf.automaticcarsgame.service.roadmap.RoadmapPositionService;
import com.ipf.automaticcarsgame.validator.game.GameAlreadyExistValidator;
import com.ipf.automaticcarsgame.validator.game.GameRequestValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
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
    private final MovementService movementService;
    private final CarCollisionService carCollisionService;
    private final RoadmapPositionRepository roadmapPositionRepository;
    private final ReturnMovement returnMovement;


    public GameService(GameRepository gameRepository,
                       GameRequestValidator gameRequestValidator,
                       GameAlreadyExistValidator gameAlreadyExistValidator,
                       GameMapper gameMapper, MovementRepository movementRepository, GameCarRepository gameCarRepository, RoadmapPositionService roadmapPositionService, MovementService movementService, CarCollisionService carCollisionService, RoadmapPositionRepository roadmapPositionRepository, ReturnMovement returnMovement) {
        this.gameRepository = gameRepository;
        this.gameRequestValidator = gameRequestValidator;
        this.gameAlreadyExistValidator = gameAlreadyExistValidator;
        this.gameMapper = gameMapper;
        this.movementRepository = movementRepository;
        this.gameCarRepository = gameCarRepository;
        this.roadmapPositionService = roadmapPositionService;
        this.movementService = movementService;
        this.carCollisionService = carCollisionService;
        this.roadmapPositionRepository = roadmapPositionRepository;
        this.returnMovement = returnMovement;
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

    @Transactional
    public synchronized Result moveCar(String carName, MovementType movementType, Integer nrOfMovemetns) {
        // todo validations

        final Optional<GameCar> gameCar = this.gameCarRepository.findGameCarByCarNameAndActiveGame(carName);
        final Position currPosition = roadmapPositionRepository.findById(gameCar.get().getPositionId()).get().getPosition();
        Result result = new Result();
        if (MovementType.LEFT.equals(movementType)) {
            changeDirectionLeft(gameCar);
        } else if (MovementType.RIGHT.equals(movementType)) {
            chageDirectionRight(gameCar);
        } else if (MovementType.FORWARD.equals(movementType)) {
            nrOfMovemetns = nrOfMovemetns == null ? 1 : nrOfMovemetns;
            final DirectionType currnetDirection = gameCar.get().getCurrnetDirection();
            final Position nextPosition = roadmapPositionService.findNextPosition(currPosition, currnetDirection, nrOfMovemetns);
            final Optional<RoadmapPosition> nextRoadmapPosition = this.roadmapPositionRepository.findByRoadmapAndPosition(gameCar.get().getGame().getRoadmap(), nextPosition);
            final boolean isRoad = this.roadmapPositionService.checkIfFieldIsCorrect(gameCar.get().getGame().getRoadmap(), nextPosition);
            if (!isRoad) {
                gameCar.get().getCar().setCrashed(true);
                result = createCrashedResult();
            } else {
                final Optional<GameCar> carOnField = this.roadmapPositionService.findCarOnField(gameCar.get().getGame().getRoadmap(), nextPosition);
                if (carOnField.isPresent()) {
                    final Car movingCar = gameCar.get().getCar();
                    final Car standingCar = carOnField.get().getCar();
                    final boolean crashMovingCar = carCollisionService.crash(movingCar, standingCar);
                    gameCar.get().setPositionId(nextRoadmapPosition.get().getId());
                    if (crashMovingCar) {
                        result = createCrashedResult();
                    }
                } else {
                    gameCar.get().setPositionId(nextRoadmapPosition.get().getId());
                }

            }
        }

        movementService.saveMovement(gameCar.get(), movementType, nrOfMovemetns);
        gameCar.get().getGame().setFinishDate(Date.from(ZonedDateTime.now().plusSeconds(5 * 60).toInstant()));

        return result;
    }

    private void chageDirectionRight(Optional<GameCar> gameCar) {
        final DirectionType currnetDirection = gameCar.get().getCurrnetDirection();
        gameCar.get().setCurrnetDirection(currnetDirection.nextRightDirection());
    }

    private void changeDirectionLeft(Optional<GameCar> gameCar) {
        final DirectionType currnetDirection = gameCar.get().getCurrnetDirection();
        gameCar.get().setCurrnetDirection(currnetDirection.nextLeftDirection());
    }

    private Result createCrashedResult() {
        return Result.ResultBuilder.builder().addError(new Result.Error("CRASHED", "Car has been crashed")).build();
    }

    @Transactional
    public Optional<Game> returnCar(String carName, int noOfMovements) {
        final Optional<GameCar> gameCar = this.gameCarRepository.findGameCarByCarNameAndActiveGame(carName);
        final Integer gameId = gameCar.get().getGameId();

        final List<Movement> movements = this.movementRepository.findMovementsInActiveGame(gameId, carName, new Date());
        final List<Movement> movments = movements.subList(0, noOfMovements);
        final List<Movement> returnMovements = returnMovement.findReturnMovements(movments);
        returnMovements.stream().forEach(mov -> {
            this.moveCar(carName, mov.getType(), mov.getNrOfMovements());
        });

        return null;
    }


}
