package com.ipf.automaticcarsgame.validator.game;

import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.game.GameRequest;
import com.ipf.automaticcarsgame.repository.RoadmapRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class GameRequestValidator implements GameValidator {

    private final RoadmapRepository roadmapRepository;

    public GameRequestValidator(RoadmapRepository roadmapRepository) {
        this.roadmapRepository = roadmapRepository;
    }

    @Override
    public Result validate(GameRequest gameRequest) {
        Result result = new Result();

        if (StringUtils.isEmpty(gameRequest.getRoadMapName())) {
            result.addError(new Result.Error("ROADMAP_NAME_EMPTY", "RoadMapName cannot be null or empty"));
        } else {
            Optional<Roadmap> roadMapOpt = roadmapRepository.findByNameIgnoreCaseAndDeleted(gameRequest.getRoadMapName(), false);

            if (!roadMapOpt.isPresent()) {
                result.addError(new Result.Error("ROADMAP_NOT_EXIST", "RoadMapName doesn't exists"));
            }
        }

        return result;
    }
}
