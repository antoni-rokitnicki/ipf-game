package com.ipf.automaticcarsgame.map.validator;


import com.ipf.automaticcarsgame.map.GameMap;

import java.util.Optional;
import java.util.Set;

class GameMapValidator {
    private final Set<Validator> validators;

    GameMapValidator(Set validators) {
        this.validators = validators;
    }

    public ValidationResult validation(GameMap gameMap) {
        final Optional<ValidationResult> first = this.validators.stream().map(validator -> validator.validate(gameMap)).filter(result -> result.isValid() == false).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return new ValidationResult(true);
    }

}
