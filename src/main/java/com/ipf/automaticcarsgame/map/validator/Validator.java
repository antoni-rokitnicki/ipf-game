package com.ipf.automaticcarsgame.map.validator;

import com.ipf.automaticcarsgame.map.GameMap;

interface Validator {
    ValidationResult validate(GameMap gameMap);
}
