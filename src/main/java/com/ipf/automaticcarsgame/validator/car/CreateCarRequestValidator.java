package com.ipf.automaticcarsgame.validator.car;

import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.dto.car.CarType;
import com.ipf.automaticcarsgame.validator.ValidationResult;
import com.ipf.automaticcarsgame.validator.ValidationResult.Error;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CreateCarRequestValidator implements CarValidator {

    @Override
    public ValidationResult validate(CarRequest carRequest) {
        ValidationResult validationResult = new ValidationResult();

        if (StringUtils.isEmpty(carRequest.getName())) {
            validationResult.addError(new Error("Car's name cannot be null"));
        }

        if (!StringUtils.isEmpty(carRequest.getType())) {
            try {
                CarType.valueOf(carRequest.getType());
            } catch (IllegalArgumentException e) {
                validationResult.addError(new Error("CarType \' " + carRequest.getType() + "\' is not correct"));
            }
        }

        return validationResult;
    }
}
