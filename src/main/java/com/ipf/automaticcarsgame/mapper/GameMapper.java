package com.ipf.automaticcarsgame.mapper;

import com.ipf.automaticcarsgame.domain.Game;
import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.dto.game.GameRequest;
import com.ipf.automaticcarsgame.repository.RoadmapRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GameMapper {

    private final RoadmapRepository roadmapRepository;


    public GameMapper(RoadmapRepository roadmapRepository) {
        this.roadmapRepository = roadmapRepository;
    }

    public Game map(GameRequest gameRequest) {
        Game game = new Game();
        Optional<Roadmap> roadmapOpt = roadmapRepository.findByNameIgnoreCaseAndDeleted(gameRequest.getRoadMapName(), false);
        roadmapOpt.ifPresent(roadmap -> game.setMapId(roadmap.getId()));

        return game;
    }
}
