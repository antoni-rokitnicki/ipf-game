package com.ipf.automaticcarsgame.repository;

import com.ipf.automaticcarsgame.domain.Roadmap;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoadmapRepository extends CrudRepository<Roadmap, Integer> {
    Optional<Roadmap> findByNameIgnoreCaseAndDeleted(String name, boolean deleted);
}
