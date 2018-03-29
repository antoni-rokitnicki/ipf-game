package com.ipf.automaticcarsgame.validator.game;

import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.dto.game.GameRequest;
import com.ipf.automaticcarsgame.repository.RoadmapRepository;
import com.ipf.automaticcarsgame.validator.ValidationResult;
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
    public ValidationResult validate(GameRequest gameRequest) {
        ValidationResult validationResult = new ValidationResult();

        if (StringUtils.isEmpty(gameRequest.getRoadMapName())) {
            validationResult.addError(new ValidationResult.Error("ROADMAP_NAME_EMPTY","RoadMapName cannot be null or empty"));
        }else {
            Optional<Roadmap> roadMapOpt = roadmapRepository.findByNameIgnoreCaseAndDeleted(gameRequest.getRoadMapName(), false);

            if(!roadMapOpt.isPresent()){
                validationResult.addError(new ValidationResult.Error("ROADMAP_NOT_EXIST","RoadMapName doesn't exists"));
            }
        }

        return validationResult;
    }
}
