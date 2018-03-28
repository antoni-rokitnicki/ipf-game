package com.ipf.automaticcarsgame.map.validator;

import com.ipf.automaticcarsgame.map.GameMap;
import com.ipf.automaticcarsgame.validator.ValidationResult;
import com.ipf.automaticcarsgame.validator.Validator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class ConnectionValidator implements Validator {

    private static final String MAP_NO_CONNECTION = "MAP_NO_CONNECTION";

    // todo jak bedzie czas
    public ValidationResult validate(GameMap gameMap) {
        if (gameMap == null || gameMap.getFields() == null || gameMap.getFields().length == 0) {
            return createError();
        }
        return createSuccessValidation();
    }

    private ValidationResult createError() {
        final ValidationResult validation = new ValidationResult();
        validation.setValid(false);
        final ValidationResult.Error error = new ValidationResult.Error();
        error.setCode(MAP_NO_CONNECTION);
        error.setMessage("No connection between fields");
        validation.setError(error);
        return validation;
    }

    private ValidationResult createSuccessValidation() {
        final ValidationResult validation = new ValidationResult();
        validation.setValid(true);
        return validation;
    }

}
