package com.ipf.automaticcarsgame.validator.car;

import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.Result.Error;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.dto.car.CarType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CarRequestValidator implements CarValidator {

    @Override
    public Result validate(CarRequest carRequest) {
        Result result = new Result();

        if (StringUtils.isEmpty(carRequest.getName())) {
            result.addError(new Error("Car's name cannot be null or empty"));
        }

        if (StringUtils.isEmpty(carRequest.getType())) {
            result.addError(new Error("CarType cannot be empty"));
        } else {
            try {
                CarType.valueOf(carRequest.getType());
            } catch (IllegalArgumentException e) {
                result.addError(new Error("CarType \' " + carRequest.getType() + "\' is not correct"));
            }
        }


        return result;
    }
}
