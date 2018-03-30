package com.ipf.automaticcarsgame.repository;

import com.ipf.automaticcarsgame.domain.Game;
import com.ipf.automaticcarsgame.domain.GameCar;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameCarRepository extends CrudRepository<GameCar, Integer> {

    @Query("select g from GameCar gc join gc.game g where gc.car.name = :carName and g.finishDate is null")
    Optional<Game> findActiveGameByCar(@Param("carName") String carName);

    @Query("select gc from GameCar gc where gc.positionId = :positionId and gc.game.finishDate is null")
    Optional<GameCar> findCarPositionIdInActiveGame(@Param("positionId") Integer positionId);
}
