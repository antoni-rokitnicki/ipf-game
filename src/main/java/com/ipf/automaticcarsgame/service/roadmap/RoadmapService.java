package com.ipf.automaticcarsgame.service.roadmap;

import com.ipf.automaticcarsgame.domain.Game;
import com.ipf.automaticcarsgame.domain.Position;
import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.domain.RoadmapPosition;
import com.ipf.automaticcarsgame.repository.GameRepository;
import com.ipf.automaticcarsgame.repository.RoadmapRepository;
import com.ipf.automaticcarsgame.validator.ValidationResult;
import com.ipf.automaticcarsgame.validator.roadmap.RoadmapValidatorProcessor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoadmapService {

    private final RoadmapValidatorProcessor roadmapValidatorProcessor;
    private final RoadmapRepository roadmapRepository;
    private final GameRepository gameRepository;

    public RoadmapService(RoadmapValidatorProcessor roadmapValidatorProcessor, RoadmapRepository roadmapRepository, GameRepository gameRepository) {
        this.roadmapValidatorProcessor = roadmapValidatorProcessor;
        this.roadmapRepository = roadmapRepository;
        this.gameRepository = gameRepository;
    }

    @Transactional
    public void createRoadmap(CreateRoadmapRequest createRoadmapRequest) {
        final ValidationResult validate = roadmapValidatorProcessor.validate(createRoadmapRequest);

        if (validate.isValid()) {
            final Roadmap roadmap = new Roadmap();
            roadmap.setName(createRoadmapRequest.getName());
            roadmap.setPositions(mapToPositionList(createRoadmapRequest.getFields()));
            this.roadmapRepository.save(roadmap);
        } else {
            validate.getErrors().forEach(System.out::println);
        }

    }

    @Transactional
    public boolean deleteRoadmap(String name) {
        final Optional<Roadmap> roadmap = this.roadmapRepository.findByNameIgnoreCaseAndDeleted(name, false);
        if (!roadmap.isPresent()) {
            return false;
        }
        final Optional<Game> game = this.gameRepository.findByRoadmap(roadmap.get());
        if (!game.isPresent()) {
            this.roadmapRepository.delete(roadmap.get());
        } else {
            roadmap.get().setDeleted(true);
        }
        return true;

    }

    /**
     * check whether a position is in Map (Matrix)
     */
    public boolean checkIfMapContainField(Roadmap roadmap, Position position) {
        return false;
    }

    /**
     * check whether a position is in Map (Matrix) and isn't a wall
     */
    public boolean checkIfFieldIsCorrect() {
        return false;
    }

    /**
     * check whether a position is occupied through other car
     */
    public boolean checkIfFieldIsOccupied(Position position) {
        return false;
    }

    private List<RoadmapPosition> mapToPositionList(int[][] fields) {
        final List<RoadmapPosition> roadmapPositions = new ArrayList<>();
        roadmapPositions.add(new RoadmapPosition(new Position(1, 1), (byte) 1));
        roadmapPositions.add(new RoadmapPosition(new Position(2, 1), (byte) 1));
        roadmapPositions.add(new RoadmapPosition(new Position(3, 1), (byte) 0));
        roadmapPositions.add(new RoadmapPosition(new Position(4, 1), (byte) 0));
        roadmapPositions.add(new RoadmapPosition(new Position(5, 1), (byte) 1));
        return roadmapPositions;
    }

}
