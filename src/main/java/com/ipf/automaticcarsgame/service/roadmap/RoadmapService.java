package com.ipf.automaticcarsgame.service.roadmap;

import com.ipf.automaticcarsgame.validator.roadmap.RoadmapValidatorProcessor;
import org.springframework.stereotype.Service;

@Service
public class RoadmapService {

    private final RoadmapValidatorProcessor roadmapValidatorProcessor;

    public RoadmapService(RoadmapValidatorProcessor roadmapValidatorProcessor) {
        this.roadmapValidatorProcessor = roadmapValidatorProcessor;
    }

    public void createRoadmap(CreateRoadmapRequest gameMap) {

    }
}
