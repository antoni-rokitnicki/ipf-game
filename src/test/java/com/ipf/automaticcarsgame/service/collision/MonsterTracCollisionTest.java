package com.ipf.automaticcarsgame.service.collision;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.dto.car.CarType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MonsterTracCollisionTest {

    private final MonsterTracCollision monsterTracCollision = new MonsterTracCollision();

    @Test
    public void shouldAliveMonsterTruck() {
        // given
        final Car standingCar = new Car();
        standingCar.setType(CarType.NORMAL_CAR);

        // when
        final CrashResult crash = monsterTracCollision.crashWith(standingCar);

        // then
        assertThat(crash.getCrashedCars().contains(CrashedCarType.MOVING_CAR)).isFalse();
        assertThat(crash.getCrashedCars().contains(CrashedCarType.STANDING_CAR)).isTrue();

    }

    @Test
    public void shouldCrashMonster() {
        // given
        final Car standingCar = new Car();
        standingCar.setType(CarType.MONSTER_TRAC);

        // when
        final CrashResult crash = monsterTracCollision.crashWith(standingCar);

        // then
        assertThat(crash.getCrashedCars().contains(CrashedCarType.MOVING_CAR)).isTrue();
        assertThat(crash.getCrashedCars().contains(CrashedCarType.STANDING_CAR)).isTrue();

    }
}