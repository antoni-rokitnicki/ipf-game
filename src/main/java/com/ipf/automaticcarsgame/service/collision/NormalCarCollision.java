package com.ipf.automaticcarsgame.service.collision;


import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.car.CarType;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
class NormalCarCollision implements Collision {

    @Override
    public CarType getCarType() {
        return CarType.NORMAL_CAR;
    }

    @Override
    public CrashResult crashWith(Car standingCar) {
        if (CarType.MONSTER_TRAC.equals(standingCar.getType())) {
            return new CrashResult(Arrays.asList(CrashedCarType.MOVING_CAR));
        } else {
            return new CrashResult(Arrays.asList(CrashedCarType.MOVING_CAR, CrashedCarType.STANDING_CAR));
        }
    }

}

