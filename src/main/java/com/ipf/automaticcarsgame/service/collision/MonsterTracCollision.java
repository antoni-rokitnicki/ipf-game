package com.ipf.automaticcarsgame.service.collision;


import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.car.CarType;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
class MonsterTracCollision implements Collision {

    @Override
    public CarType getCarType() {
        return CarType.MONSTER_TRAC;
    }

    @Override
    public CrashResult crash(Car standingCar) {
        if (getCarType().equals(standingCar.getType())) {
            return new CrashResult(Arrays.asList(CrashedCarType.MOVING_CAR, CrashedCarType.STANDING_CAR));
        } else {
            return new CrashResult(Arrays.asList(CrashedCarType.STANDING_CAR));
        }
    }

}

