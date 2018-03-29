package com.ipf.automaticcarsgame.mapper;

import com.ipf.automaticcarsgame.domain.Position;
import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.domain.RoadmapPosition;
import com.ipf.automaticcarsgame.dto.roadmap.RoadmapRequest;

import java.util.ArrayList;
import java.util.List;

public class RoadmapMapper {

    private RoadmapMapper() {
    }

    public static Roadmap mapToRoadmap(RoadmapRequest roadmapRequest) {
        final Roadmap roadmap = new Roadmap();
        roadmap.setName(roadmapRequest.getName());
        final List<RoadmapPosition> roadmapPositions = new ArrayList<>();
        for (int i = 0; i < roadmapRequest.getFields().length; i++) {
            for (int k = 0; k < roadmapRequest.getFields()[i].length; k++) {
                roadmapPositions.add(new RoadmapPosition(new Position(i, k), (byte) roadmapRequest.getFields()[i][k]));
            }
        }
        roadmap.setPositions(roadmapPositions);
        return roadmap;
    }

}
