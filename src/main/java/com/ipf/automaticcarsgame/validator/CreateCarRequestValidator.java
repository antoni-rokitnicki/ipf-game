package com.ipf.automaticcarsgame.validator;

import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.dto.ResponseError;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.dto.car.CarType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CreateCarRequestValidator implements Validator<CarRequest> {

    @Override
    public Response<CarRequest> validate(CarRequest carRequest) {
        ResponseError responseError = new ResponseError(400);

        if (StringUtils.isEmpty(carRequest.getName())) {
            responseError.addMessage("Car's name cannot be null");
        }

        if (!StringUtils.isEmpty(carRequest.getType())) {
            try {
                CarType.valueOf(carRequest.getType());
            } catch (IllegalArgumentException e) {
                responseError.addMessage("CarType \' " + carRequest.getType() + "\' is not correct");
            }
        }

        return responseError.getMessages().size() == 0 ? new Response<>() : new Response<>(responseError);
    }
}
