package com.ipf.automaticcarsgame.validator;

import com.ipf.automaticcarsgame.map.GameMap;
import com.ipf.automaticcarsgame.map.validator.ValidationResult;

public interface Validator {
    ValidationResult validate(GameMap gameMap);
}
