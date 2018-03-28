package com.ipf.automaticcarsgame.validator.roadmap;

import com.ipf.automaticcarsgame.service.roadmap.CreateRoadmapRequest;
import com.ipf.automaticcarsgame.validator.ValidationResult;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class ConnectionValidator implements RoadmapValidator {

    private static final String MAP_NO_CONNECTION = "MAP_NO_CONNECTION";

    // todo jak bedzie czas
    public ValidationResult validate(CreateRoadmapRequest gameMap) {
        if (gameMap == null || gameMap.getFields() == null || gameMap.getFields().length == 0) {
            return createError();
        }
        return createSuccessValidation();
    }

    private ValidationResult createError() {
        final ValidationResult validation = new ValidationResult();
        final ValidationResult.Error error = new ValidationResult.Error();
        error.setCode(MAP_NO_CONNECTION);
        error.setMessage("No connection between fields");
        validation.addError(error);
        return validation;
    }
}
