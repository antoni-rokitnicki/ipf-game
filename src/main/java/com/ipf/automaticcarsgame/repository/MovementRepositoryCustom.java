package com.ipf.automaticcarsgame.repository;

import com.ipf.automaticcarsgame.domain.GameCar;
import com.ipf.automaticcarsgame.domain.Movement;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementRepositoryCustom {
    List<Movement> findMovements(GameCar gameCar, Integer limit);
}

