package com.ipf.automaticcarsgame.service.roadmap;

import com.ipf.automaticcarsgame.domain.Game;
import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.repository.GameRepository;
import com.ipf.automaticcarsgame.repository.RoadmapRepository;
import com.ipf.automaticcarsgame.validator.roadmap.RoadmapValidatorProcessor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public void createRoadmap(CreateRoadmapRequest gameMap) {

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
}
