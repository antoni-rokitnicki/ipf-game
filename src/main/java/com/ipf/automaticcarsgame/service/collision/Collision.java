package com.ipf.automaticcarsgame.service.collision;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.car.CarType;

interface Collision {

    CarType getCarType();

    CrashResult crash(Car standingCar);

}
