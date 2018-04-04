package com.ipf.automaticcarsgame.repository;

import com.ipf.automaticcarsgame.domain.Game;
import com.ipf.automaticcarsgame.domain.Roadmap;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {
    Optional<Game> findByRoadmap(Roadmap roadmap);

    @Query("select g from Game g where g.roadmap.name = :mapName and g.finishDate > CURRENT_TIMESTAMP ")
    Optional<Game> findActiveGameByRoadMapName(@Param("mapName") String roadMapName);

}

