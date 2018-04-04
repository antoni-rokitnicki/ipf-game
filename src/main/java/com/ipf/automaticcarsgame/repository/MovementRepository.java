package com.ipf.automaticcarsgame.repository;

import com.ipf.automaticcarsgame.domain.Movement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MovementRepository extends CrudRepository<Movement, Integer> {

    @Query("select m from Movement m where m.gameCar.game.id = :gameId and m.gameCar.car.name = :carName and m.gameCar.game.finishDate > current_timestamp ORDER BY insertDate DESC ")
    List<Movement> findMovementsInActiveGame(@Param("gameId") Integer gameId, @Param("carName") String carName);

}
