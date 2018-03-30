package com.ipf.automaticcarsgame.validator.roadmap;


import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.roadmap.RoadmapRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoadmapValidatorProcessor {
    private final List<RoadmapValidator> validators;

    RoadmapValidatorProcessor(List<RoadmapValidator> validators) {
        this.validators = validators;
    }

    public Result validate(RoadmapRequest gameMap) {
        return this.validators.stream()
                .map(validator -> validator.validate(gameMap))
                .filter(result -> !result.isValid())
                .findFirst().orElseGet(Result::new);

    }

}
