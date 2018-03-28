package com.ipf.automaticcarsgame.validator;

import com.ipf.automaticcarsgame.map.GameMap;

public interface Validator {
    ValidationResult validate(GameMap gameMap);
}
