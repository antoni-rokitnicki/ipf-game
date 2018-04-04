package com.ipf.automaticcarsgame.service.roadmap;

import com.ipf.automaticcarsgame.domain.*;
import com.ipf.automaticcarsgame.repository.GameCarRepository;
import com.ipf.automaticcarsgame.repository.RoadmapPositionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoadmapPositionService {

    private final GameCarRepository gameCarRepository;
    private final RoadmapPositionRepository roadmapPositionRepository;

    public RoadmapPositionService(GameCarRepository gameCarRepository, RoadmapPositionRepository roadmapPositionRepository) {
        this.gameCarRepository = gameCarRepository;
        this.roadmapPositionRepository = roadmapPositionRepository;
    }

    /**
     * check whether a position is in scope Map (Matrix) and isn't a wall
     */
    public boolean checkIfFieldIsCorrect(Roadmap roadmap, Position position) {
        Optional<RoadmapPosition> roadmapPositionOpt = roadmapPositionRepository.findByRoadmapAndPosition(roadmap, position);

        return roadmapPositionOpt.filter(roadmapPosition -> roadmapPosition.getValue().compareTo((byte) 0) > 0).isPresent();
    }

    public boolean checkIfFieldIsCorrect(Roadmap roadmap, Position currentPosition, DirectionType moveDirection, int nrOfMoves) {
        return checkIfFieldIsCorrect(roadmap, findNextPosition(currentPosition, moveDirection, nrOfMoves));
    }

    /**
     * check whether a position is occupied through other car
     */
    public boolean checkIfFieldIsOccupied(Roadmap roadmap, Position position) {
        Optional<RoadmapPosition> roadmapPositionOpt = roadmapPositionRepository.findByRoadmapAndPosition(roadmap, position);

        Optional<GameCar> gameCarOpt = Optional.empty();
        if (roadmapPositionOpt.isPresent()) {
            gameCarOpt = gameCarRepository.findCarPositionIdInActiveGame(roadmapPositionOpt.get().getId());
        }

        return gameCarOpt.isPresent();
    }

    public Optional<GameCar> findCarOnField(Roadmap roadmap, Position position) {
        Optional<RoadmapPosition> roadmapPositionOpt = roadmapPositionRepository.findByRoadmapAndPosition(roadmap, position);
        Optional<GameCar> gameCarOpt = Optional.empty();
        if (roadmapPositionOpt.isPresent()) {
            gameCarOpt = gameCarRepository.findCarPositionIdInActiveGame(roadmapPositionOpt.get().getId());
        }
        return gameCarOpt;
    }

    public Optional<GameCar> findCarOnField(Roadmap roadmap, Position currentPosition, DirectionType moveDirection, int nrOfMoves) {
        return findCarOnField(roadmap, findNextPosition(currentPosition, moveDirection, nrOfMoves));
    }


    public boolean checkIfFieldIsOccupied(Roadmap roadmap, Position currentPosition, DirectionType moveDirection, int nrOfMoves) {
        return checkIfFieldIsOccupied(roadmap, findNextPosition(currentPosition, moveDirection, nrOfMoves));
    }

    public Position findNextPosition(Position currentPosition, DirectionType moveDirection, int nrOfMoves) {
        final Integer row = currentPosition.getRow();
        final Integer col = currentPosition.getCol();
        if (DirectionType.NORTH.equals(moveDirection)) {
            return new Position(row - nrOfMoves, col);
        } else if (DirectionType.SOUTH.equals(moveDirection)) {
            return new Position(row + nrOfMoves, col);
        } else if (DirectionType.WEST.equals(moveDirection)) {
            return new Position(row, col - nrOfMoves);
        } else if (DirectionType.EAST.equals(moveDirection)) {
            return new Position(row, col + nrOfMoves);
        } else {
            throw new UnsupportedOperationException("Unsupported moveDirection: " + moveDirection);
        }
    }

    public Position findCurrentPosition(Optional<GameCar> movingGameCar) {
        return roadmapPositionRepository.findById(movingGameCar.get().getPositionId()).get().getPosition();
    }

    public Optional<RoadmapPosition> findByRoadmapAndPosition(Optional<GameCar> movingGameCar, Position nextPosition) {
        return this.roadmapPositionRepository.findByRoadmapAndPosition(movingGameCar.get().getGame().getRoadmap(), nextPosition);
    }

}
