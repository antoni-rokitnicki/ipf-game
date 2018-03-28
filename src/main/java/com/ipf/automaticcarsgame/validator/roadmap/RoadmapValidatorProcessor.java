package com.ipf.automaticcarsgame.validator.roadmap;


import com.ipf.automaticcarsgame.service.roadmap.CreateRoadmapRequest;
import com.ipf.automaticcarsgame.validator.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoadmapValidatorProcessor {
    private final List<RoadmapValidator> validators;

    RoadmapValidatorProcessor(List<RoadmapValidator> validators) {
        this.validators = validators;
    }

    public ValidationResult validate(CreateRoadmapRequest gameMap) {
        return this.validators.stream()
                .map(validator -> validator.validate(gameMap))
                .filter(result -> !result.isValid())
                .findFirst().orElseGet(ValidationResult::new);

    }

}
