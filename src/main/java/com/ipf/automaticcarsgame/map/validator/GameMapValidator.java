package com.ipf.automaticcarsgame.map.validator;


import com.ipf.automaticcarsgame.map.GameMap;
import com.ipf.automaticcarsgame.validator.ValidationResult;
import com.ipf.automaticcarsgame.validator.Validator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class GameMapValidator {
    private final List<Validator> validators;

    GameMapValidator(List<Validator> validators) {
        this.validators = validators;
        for (Validator validator : this.validators) {
            System.out.println(validator);
        }
    }

    public ValidationResult validation(GameMap gameMap) {
        final Optional<ValidationResult> first = this.validators.stream().map(validator -> validator.validate(gameMap)).filter(result -> !result.isValid()).findFirst();
        return first.orElseGet(ValidationResult::new);
    }

}
