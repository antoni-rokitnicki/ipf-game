package com.ipf.automaticcarsgame.map.validator;

import com.ipf.automaticcarsgame.map.GameMap;
import com.ipf.automaticcarsgame.validator.ValidationResult;
import com.ipf.automaticcarsgame.validator.Validator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Order(1)
public class DimensionValidator implements IGameMapValidator {
    private static final String MAP_INCORRECT_DIMENSION = "MAP_INCORRECT_DIMENSION";

    public ValidationResult validate(GameMap gameMap) {
        if (gameMap == null || gameMap.getFields() == null || gameMap.getFields().length == 0) {
            return createError();
        }
        final boolean incorrectDimension = Arrays.stream(gameMap.getFields()).filter(row -> row.length != gameMap.getFields().length).findAny().isPresent();
        if (incorrectDimension) {
            return createError();
        }
        return createSuccessValidation();
    }

    private ValidationResult createError() {
        final ValidationResult validation = new ValidationResult();
        final ValidationResult.Error error = new ValidationResult.Error();
        error.setCode(MAP_INCORRECT_DIMENSION);
        error.setMessage("Map must be square");
        validation.addError(error);
        return validation;
    }

}
