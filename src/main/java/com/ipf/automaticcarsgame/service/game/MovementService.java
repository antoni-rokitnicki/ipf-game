package com.ipf.automaticcarsgame.service.game;

import com.ipf.automaticcarsgame.domain.*;
import com.ipf.automaticcarsgame.dto.MovementType;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.repository.GameCarRepository;
import com.ipf.automaticcarsgame.repository.MovementRepository;
import com.ipf.automaticcarsgame.repository.RoadmapPositionRepository;
import com.ipf.automaticcarsgame.service.collision.CarCollisionService;
import com.ipf.automaticcarsgame.service.roadmap.RoadmapPositionService;
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
    private final RoadmapPositionRepository roadmapPositionRepository;
    private final ReturnMovement returnMovement;

    public MovementService(MovementRepository movementRepository, GameCarRepository gameCarRepository, RoadmapPositionService roadmapPositionService, CarCollisionService carCollisionService, RoadmapPositionRepository roadmapPositionRepository, ReturnMovement returnMovement) {
        this.movementRepository = movementRepository;
        this.gameCarRepository = gameCarRepository;
        this.roadmapPositionService = roadmapPositionService;
        this.carCollisionService = carCollisionService;
        this.roadmapPositionRepository = roadmapPositionRepository;
        this.returnMovement = returnMovement;
    }


    private void saveMovement(GameCar gameCar, MovementType movementType, Integer nrOfMovemetns) {
        final Movement movement = new Movement();
        movement.setType(movementType);
        movement.setGameCar(gameCar);
        movement.setGameCarId(gameCar.getId());
        movement.setNrOfMovements(nrOfMovemetns);
        movementRepository.save(movement);
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

        saveMovement(gameCar.get(), movementType, nrOfMovemetns);
        gameCar.get().getGame().setFinishDate(Date.from(ZonedDateTime.now().plusSeconds(5 * 60).toInstant()));

        return result;
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


}
