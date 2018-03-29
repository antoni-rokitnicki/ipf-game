package com.ipf.automaticcarsgame.validator.roadmap;

import com.ipf.automaticcarsgame.service.roadmap.CreateRoadmapRequest;
import com.ipf.automaticcarsgame.dto.Result;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class ConnectionValidator implements RoadmapValidator {

    private static final String MAP_NO_CONNECTION = "MAP_NO_CONNECTION";

    public Result validate(CreateRoadmapRequest gameMap) {
        if (gameMap == null || gameMap.getFields() == null || gameMap.getFields().length == 0) {
            return createError();
        }

        return createSuccessValidation();
    }

    private Result createError() {
        final Result validation = new Result();
        final Result.Error error = new Result.Error();
        error.setCode(MAP_NO_CONNECTION);
        error.setMessage("No connection between fields");
        validation.addError(error);
        return validation;
    }
}
