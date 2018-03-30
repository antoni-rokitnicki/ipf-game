package com.ipf.automaticcarsgame.service.roadmap;

import com.ipf.automaticcarsgame.domain.*;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.roadmap.RoadmapRequest;
import com.ipf.automaticcarsgame.mapper.RoadmapMapper;
import com.ipf.automaticcarsgame.repository.GameCarRepository;
import com.ipf.automaticcarsgame.repository.GameRepository;
import com.ipf.automaticcarsgame.repository.RoadmapPositionRepository;
import com.ipf.automaticcarsgame.repository.RoadmapRepository;
import com.ipf.automaticcarsgame.validator.roadmap.RoadmapValidatorProcessor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class RoadmapService {

    private final RoadmapValidatorProcessor roadmapValidatorProcessor;
    private final RoadmapRepository roadmapRepository;
    private final GameRepository gameRepository;

    public RoadmapService(RoadmapValidatorProcessor roadmapValidatorProcessor,
                          RoadmapRepository roadmapRepository,
                          RoadmapPositionRepository roadmapPositionRepository,
                          GameRepository gameRepository,
                          GameCarRepository gameCarRepository) {
        this.roadmapValidatorProcessor = roadmapValidatorProcessor;
        this.roadmapRepository = roadmapRepository;
        this.gameRepository = gameRepository;
    }

    @Transactional
    public Result createRoadmap(RoadmapRequest createRoadmapRequest) {
        final Result result = roadmapValidatorProcessor.validate(createRoadmapRequest);
        if (result.isValid()) {
            final Roadmap roadmap = RoadmapMapper.mapToRoadmap(createRoadmapRequest);
            this.roadmapRepository.save(roadmap);
            return Result.ResultBuilder.builder().build();
        } else {
            return result;
        }
    }

    @Transactional
    public Result deleteRoadmap(String name) {
        final Optional<Roadmap> roadmap = this.roadmapRepository.findByNameIgnoreCaseAndDeleted(name, false);
        if (!roadmap.isPresent()) {
            return createNotExistErrorResult();
        }
        final Optional<Game> game = this.gameRepository.findByRoadmap(roadmap.get());
        if (!game.isPresent()) {
            this.roadmapRepository.delete(roadmap.get());
        } else {
            roadmap.get().setDeleted(true);
        }
        return Result.ResultBuilder.builder().build();
    }

    public List<Roadmap> findAll() {
        return (List<Roadmap>) this.roadmapRepository.findAll();
    }

    private Result createNotExistErrorResult() {
        return Result.ResultBuilder.builder().addError(new Result.Error("DOES_NOT_EXIST", "Roadmap does not exist")).build();
    }

}
