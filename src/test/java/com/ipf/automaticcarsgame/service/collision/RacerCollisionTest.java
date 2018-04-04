package com.ipf.automaticcarsgame.service.collision;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.car.CarType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class RacerCollisionTest {

    private final RacerCollision racerCollision = new RacerCollision();

    @Test
    public void shouldCrashBothCar() {
        // given
        final Car standingCar = new Car();
        standingCar.setType(CarType.NORMAL_CAR);

        // when
        final CrashResult crash = racerCollision.crashWith(standingCar);

        // then
        assertThat(crash.getCrashedCars().contains(CrashedCarType.MOVING_CAR)).isTrue();
        assertThat(crash.getCrashedCars().contains(CrashedCarType.STANDING_CAR)).isTrue();

    }

    @Test
    public void shouldCrashOnlyRacer() {
        // given
        final Car standingCar = new Car();
        standingCar.setType(CarType.MONSTER_TRAC);

        // when
        final CrashResult crash = racerCollision.crashWith(standingCar);

        // then
        assertThat(crash.getCrashedCars().contains(CrashedCarType.MOVING_CAR)).isTrue();
        assertThat(crash.getCrashedCars().contains(CrashedCarType.STANDING_CAR)).isFalse();

    }
}