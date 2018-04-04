package com.ipf.automaticcarsgame.service.game;

import com.ipf.automaticcarsgame.domain.*;
import com.ipf.automaticcarsgame.dto.MovementType;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.repository.GameCarRepository;
import com.ipf.automaticcarsgame.repository.MovementRepository;
import com.ipf.automaticcarsgame.service.collision.CarCollisionService;
import com.ipf.automaticcarsgame.service.collision.CrashResult;
import com.ipf.automaticcarsgame.service.collision.CrashedCarType;
import com.ipf.automaticcarsgame.service.roadmap.RoadmapPositionService;
import com.ipf.automaticcarsgame.validator.game.movement.MoveCarValidator;
import com.ipf.automaticcarsgame.validator.game.movement.ReturnCarValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MovementService {
    private final MovementRepository movementRepository;
    private final GameCarRepository gameCarRepository;
    private final RoadmapPositionService roadmapPositionService;
    private final CarCollisionService carCollisionService;
    private final ReturnRouteFinder returnRouteFinder;
    private final Integer idlenessTimeSecond;
    private final MoveCarValidator moveCarValidator;
    private final ReturnCarValidator returnCarValidator;

    public MovementService(MovementRepository movementRepository,
                           GameCarRepository gameCarRepository,
                           RoadmapPositionService roadmapPositionService,
                           CarCollisionService carCollisionService,
                           ReturnRouteFinder returnRouteFinder,
                           @Value("${game.idleness.timeSecond}") Integer idlenessTimeSecond, MoveCarValidator moveCarValidator, ReturnCarValidator returnCarValidator) {
        this.movementRepository = movementRepository;
        this.gameCarRepository = gameCarRepository;
        this.roadmapPositionService = roadmapPositionService;
        this.carCollisionService = carCollisionService;
        this.returnRouteFinder = returnRouteFinder;
        this.idlenessTimeSecond = idlenessTimeSecond;
        this.moveCarValidator = moveCarValidator;
        this.returnCarValidator = returnCarValidator;
    }

    @Transactional
    public synchronized Result moveCar(String carName, MovementType movementType, Integer nrOfMovemetns) {
        final Result validationResult = this.moveCarValidator.validate(carName, nrOfMovemetns);
        if (!validationResult.isValid()) {
            return validationResult;
        }

        final Optional<GameCar> movingGameCar = this.gameCarRepository.findGameCarByCarNameAndActiveGame(carName);
        final Position currPosition = this.roadmapPositionService.findCurrentPosition(movingGameCar);
        Result result = new Result();
        if (MovementType.LEFT.equals(movementType)) {
            changeDirectionLeft(movingGameCar);
        } else if (MovementType.RIGHT.equals(movementType)) {
            changeDirectionRight(movingGameCar);
        } else if (MovementType.FORWARD.equals(movementType)) {
            result = moveForward(nrOfMovemetns, movingGameCar, currPosition);
        }

        saveMovement(movingGameCar.get(), movementType, nrOfMovemetns);
        movingGameCar.get().getGame().setFinishDate(Date.from(ZonedDateTime.now().plusSeconds(idlenessTimeSecond).toInstant()));

        return result;
    }


    @Transactional
    public Result returnCar(String carName, int noOfMovements) {
        final Result validationResult = this.returnCarValidator.validate(carName, noOfMovements);
        if (!validationResult.isValid()) {
            return validationResult;
        }
        final Optional<GameCar> gameCar = this.gameCarRepository.findGameCarByCarNameAndActiveGame(carName);
        final Integer gameId = gameCar.get().getGameId();
        final List<Movement> movements = this.movementRepository.findMovementsInActiveGame(gameId, carName);
        final List<Movement> movments = movements.subList(0, Math.min(noOfMovements, movements.size()));
        final List<Movement> returnMovements = returnRouteFinder.findReturnMovements(movments);
        returnMovements.stream().forEach(mov -> {
            this.moveCar(carName, mov.getType(), mov.getNrOfMovements()); //todo create Timer
        });
        return Result.ResultBuilder.builder().build();
    }

    private Result moveForward(Integer nrOfMovemetns, Optional<GameCar> movingGameCar, Position currPosition) {
        Result result = new Result();
        final DirectionType currnetDirection = movingGameCar.get().getCurrnetDirection();
        final Position nextPosition = roadmapPositionService.findNextPosition(currPosition, currnetDirection, nrOfMovemetns);
        final Optional<RoadmapPosition> nextRoadmapPosition = this.roadmapPositionService.findByRoadmapAndPosition(movingGameCar, nextPosition);
        final boolean isRoad = this.roadmapPositionService.checkIfFieldIsCorrect(movingGameCar.get().getGame().getRoadmap(), nextPosition);
        if (!isRoad) {
            movingGameCar.get().getCar().setCrashed(true);
            movingGameCar.get().setDeleted(true);
            result = createCrashedResult();
        } else {
            final Optional<GameCar> standingGameCar = this.roadmapPositionService.findCarOnField(movingGameCar.get().getGame().getRoadmap(), nextPosition);
            if (standingGameCar.isPresent()) {
                final Car movingCar = movingGameCar.get().getCar();
                final Car standingCar = standingGameCar.get().getCar();
                final CrashResult crashResult = carCollisionService.crash(movingCar, standingCar);
                movingGameCar.get().setPositionId(nextRoadmapPosition.get().getId());
                if (crashResult.getCrashedCars().contains(CrashedCarType.MOVING_CAR)) {
                    movingGameCar.get().setDeleted(true);
                    result = createCrashedResult();
                }
                if (crashResult.getCrashedCars().contains(CrashedCarType.STANDING_CAR)) {
                    standingGameCar.get().setDeleted(true);
                }
            } else {
                movingGameCar.get().setPositionId(nextRoadmapPosition.get().getId());
            }
        }
        return result;
    }


    private void saveMovement(GameCar gameCar, MovementType movementType, Integer nrOfMovemetns) {
        final Movement movement = new Movement();
        movement.setType(movementType);
        movement.setGameCar(gameCar);
        movement.setGameCarId(gameCar.getId());
        movement.setNrOfMovements(MovementType.FORWARD.equals(movementType) ? nrOfMovemetns : null);
        movementRepository.save(movement);
    }

    private void changeDirectionRight(Optional<GameCar> gameCar) {
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


}
