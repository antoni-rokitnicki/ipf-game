package com.ipf.automaticcarsgame.mapper;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.dto.car.CarType;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public Car map(CarRequest carRequest) {
        Car car = new Car();
        car.setName(carRequest.getName());

        CarType carType = CarType.valueOf(carRequest.getType());
        car.setType(carType);

        return car;
    }
}
