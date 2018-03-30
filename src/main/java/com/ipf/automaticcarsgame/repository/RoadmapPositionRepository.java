package com.ipf.automaticcarsgame.repository;

import com.ipf.automaticcarsgame.domain.Position;
import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.domain.RoadmapPosition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoadmapPositionRepository extends CrudRepository<RoadmapPosition, Integer> {

    Optional<RoadmapPosition> findByRoadmapAndPosition(Roadmap roadmap, Position position);
}
