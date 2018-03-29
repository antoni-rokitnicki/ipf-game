package com.ipf.automaticcarsgame.validator.roadmap;

import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.roadmap.RoadmapRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Order(1)
public class DimensionValidator implements RoadmapValidator {
    private static final String INCORRECT_DIMENSION = "INCORRECT_DIMENSION";

    public Result validate(RoadmapRequest gameMap) {
        if (gameMap == null || gameMap.getFields() == null || gameMap.getFields().length == 0) {
            return createError(INCORRECT_DIMENSION, "Empty value");
        }
        final boolean incorrectDimension = Arrays.stream(gameMap.getFields()).filter(row -> row.length != gameMap.getFields().length).findAny().isPresent();
        if (incorrectDimension) {
            return createError(INCORRECT_DIMENSION, "Map must be square");
        }
        return createSuccessValidation();
    }

}
