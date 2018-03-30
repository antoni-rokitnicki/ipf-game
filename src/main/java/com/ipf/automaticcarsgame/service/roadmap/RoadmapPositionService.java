package com.ipf.automaticcarsgame.service.roadmap;

import com.ipf.automaticcarsgame.domain.GameCar;
import com.ipf.automaticcarsgame.domain.Position;
import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.domain.RoadmapPosition;
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
}
