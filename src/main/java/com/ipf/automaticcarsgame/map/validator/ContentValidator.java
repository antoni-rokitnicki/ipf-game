package com.ipf.automaticcarsgame.map.validator;

import com.ipf.automaticcarsgame.map.GameMap;

import java.util.Arrays;

public class ContentValidator implements Validator {

    private static final String MAP_INVALID_CONTENT = "MAP_INVALID_CONTENT";

    public ValidationResult validate(GameMap gameMap) {
        if (gameMap == null || gameMap.getFields() == null || gameMap.getFields().length == 0) {
            return createError();
        }

        final boolean incorrectDigits = Arrays.stream(gameMap.getFields()).filter(row -> row.length != 0 && row.length == 1).findAny().isPresent();
        if (incorrectDigits) {
            return createError();
        }
        return createSuccessValidation();
    }

    private ValidationResult createError() {
        final ValidationResult validation = new ValidationResult();
        validation.setValid(false);
        final ValidationResult.Error error = new ValidationResult.Error();
        error.setCode(MAP_INVALID_CONTENT);
        error.setMessage("Invalid content. Supported only 0 or 1");
        validation.setError(error);
        return validation;
    }

    private ValidationResult createSuccessValidation() {
        final ValidationResult validation = new ValidationResult();
        validation.setValid(true);
        return validation;
    }

}
