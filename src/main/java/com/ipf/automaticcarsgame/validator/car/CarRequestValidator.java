package com.ipf.automaticcarsgame.validator.car;

import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.Result.Error;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.dto.car.CarType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CarRequestValidator implements CarValidator {
    private static final String EMPTY_VALUE = "EMPTY_VALUE";
    private static final String EMPTY_VALUE_CAR_NAME_MESSAGE = "Car's name cannot be null or empty";
    private static final String EMPTY_VALUE_TYPE_MESSAGE = "CarType cannot be empty";
    private static final String INCORECT_TYPE = "INCORECT_TYPE";

    @Override
    public Result validate(CarRequest carRequest) {
        Result result = new Result();

        if (StringUtils.isEmpty(carRequest.getName())) {
            result.addError(new Error(EMPTY_VALUE, EMPTY_VALUE_CAR_NAME_MESSAGE));
        }

        if (StringUtils.isEmpty(carRequest.getType())) {
            result.addError(new Error(EMPTY_VALUE, EMPTY_VALUE_TYPE_MESSAGE));
        } else {
            try {
                CarType.valueOf(carRequest.getType());
            } catch (IllegalArgumentException e) {
                result.addError(new Error(INCORECT_TYPE, "CarType '" + carRequest.getType() + "' is not correct"));
            }
        }


        return result;
    }
}
