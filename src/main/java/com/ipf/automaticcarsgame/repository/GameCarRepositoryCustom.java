package com.ipf.automaticcarsgame.repository;

import com.ipf.automaticcarsgame.domain.GameCar;

import java.util.List;

public interface GameCarRepositoryCustom {

    List<GameCar> findGamesCar(List<Integer> gameIds, List<String> mapNames, List<String> carNames);

}

