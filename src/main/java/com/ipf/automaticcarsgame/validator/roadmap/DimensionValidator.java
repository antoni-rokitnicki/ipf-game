package com.ipf.automaticcarsgame.validator.roadmap;

import com.ipf.automaticcarsgame.dto.roadmap.RoadmapRequest;
import com.ipf.automaticcarsgame.dto.Result;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Order(1)
public class DimensionValidator implements RoadmapValidator {
    private static final String INCORRECT_DIMENSION = "INCORRECT_DIMENSION";

    public Result validate(RoadmapRequest gameMap) {
        if (gameMap == null || gameMap.getFields() == null || gameMap.getFields().length == 0) {
            return createError();
        }
        final boolean incorrectDimension = Arrays.stream(gameMap.getFields()).filter(row -> row.length != gameMap.getFields().length).findAny().isPresent();
        if (incorrectDimension) {
            return createError();
        }
        return createSuccessValidation();
    }

    private Result createError() {
        final Result validation = new Result();
        final Result.Error error = new Result.Error();
        error.setCode(INCORRECT_DIMENSION);
        error.setMessage("Map must be square");
        validation.addError(error);
        return validation;
    }

}
