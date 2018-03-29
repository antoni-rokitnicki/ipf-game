package com.ipf.automaticcarsgame.mapper;

import com.ipf.automaticcarsgame.domain.Position;
import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.domain.RoadmapPosition;
import com.ipf.automaticcarsgame.service.roadmap.CreateRoadmapRequest;

import java.util.ArrayList;
import java.util.List;

public class RoadmapMapper {

    private RoadmapMapper() {
    }

    public static Roadmap mapToRoadmap(CreateRoadmapRequest createRoadmapRequest) {
        final Roadmap roadmap = new Roadmap();
        roadmap.setName(createRoadmapRequest.getName());
        final List<RoadmapPosition> roadmapPositions = new ArrayList<>();
        for (int i = 0; i < createRoadmapRequest.getFields().length; i++) {
            for (int k = 0; k < createRoadmapRequest.getFields()[i].length; k++) {
                roadmapPositions.add(new RoadmapPosition(new Position(i, k), (byte) createRoadmapRequest.getFields()[i][k]));
            }
        }
        roadmap.setPositions(roadmapPositions);
        return roadmap;
    }

}
