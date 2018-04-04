package com.ipf.automaticcarsgame.service.collision;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.car.CarType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Service
public class CarCollisionService {

    private final Map<CarType, Collision> collisionMap;

    public CarCollisionService(List<Collision> collisionMap) {
        this.collisionMap = collisionMap.stream().collect(toMap(Collision::getCarType, collision -> collision));
    }

    public CrashResult crash(Car movingCar, Car standingCar) {
        final CrashResult crashResult = this.collisionMap.get(movingCar.getType()).crashWith(standingCar);
        if (crashResult.getCrashedCars().contains(CrashedCarType.MOVING_CAR)) {
            movingCar.setCrashed(true);
        }
        if (crashResult.getCrashedCars().contains(CrashedCarType.STANDING_CAR)) {
            standingCar.setCrashed(true);
        }
        return crashResult;
    }


}
