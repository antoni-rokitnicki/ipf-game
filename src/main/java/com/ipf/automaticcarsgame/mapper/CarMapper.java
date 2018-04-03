package com.ipf.automaticcarsgame.mapper;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.car.CarDto;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.dto.car.CarType;

import java.util.ArrayList;
import java.util.List;


public class CarMapper {

    private CarMapper() {
    }

    public static Car map(CarRequest carRequest) {
        Car car = new Car();
        car.setName(carRequest.getName());

        CarType carType = CarType.valueOf(carRequest.getType());
        car.setType(carType);

        return car;
    }

    public static List<CarDto> map(Iterable<Car> cars) {
        List<CarDto> carRespons = new ArrayList<>();
        for (Car car : cars) {
            carRespons.add(map(car));
        }

        return carRespons;
    }

    public static CarDto map(Car car) {
        CarDto carDto = new CarDto();
        carDto.setCarName(car.getName());
        carDto.setType(car.getType());
        carDto.setCrashed(car.isCrashed());
        carDto.setInsertDate(car.getInsertDate());
        carDto.setUpdateDate(car.getUpdateDate());

        return carDto;
    }


}
