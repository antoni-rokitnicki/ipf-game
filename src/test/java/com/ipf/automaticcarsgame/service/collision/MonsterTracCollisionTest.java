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
        final CrashResult crash = monsterTracCollision.crash(standingCar);

        // then
        assertThat(crash.getCrashedCar().contains(CrashedCarType.MOVING_CAR)).isFalse();
        assertThat(crash.getCrashedCar().contains(CrashedCarType.STANDING_CAR)).isTrue();

    }

    @Test
    public void shouldCrashMonster() {
        // given
        final Car standingCar = new Car();
        standingCar.setType(CarType.MONSTER_TRAC);

        // when
        final CrashResult crash = monsterTracCollision.crash(standingCar);

        // then
        assertThat(crash.getCrashedCar().contains(CrashedCarType.MOVING_CAR)).isTrue();
        assertThat(crash.getCrashedCar().contains(CrashedCarType.STANDING_CAR)).isTrue();

    }
}