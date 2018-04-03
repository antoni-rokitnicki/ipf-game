package com.ipf.automaticcarsgame.mapper;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.dto.car.CarResponse;
import com.ipf.automaticcarsgame.dto.car.CarType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarMapper {

    public Car map(CarRequest carRequest) {
        Car car = new Car();
        car.setName(carRequest.getName());

        CarType carType = CarType.valueOf(carRequest.getType());
        car.setType(carType);

        return car;
    }

    public List<CarResponse> map(Iterable<Car> cars) {
        List<CarResponse> carResponses = new ArrayList<>();
        for (Car car : cars) {
            carResponses.add(map(car));
        }

        return carResponses;
    }

    public CarResponse map(Car car) {
        CarResponse carResponse = new CarResponse();
        carResponse.setCarName(car.getName());
        carResponse.setType(car.getType());
        carResponse.setCrashed(car.isCrashed());
        carResponse.setInsertDate(car.getInsertDate());
        carResponse.setUpdateDate(car.getUpdateDate());

        return carResponse;
    }


}
