package com.ipf.automaticcarsgame.mapper;

import com.ipf.automaticcarsgame.domain.Position;
import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.domain.RoadmapPosition;
import com.ipf.automaticcarsgame.dto.roadmap.RoadmapPositionDto;
import com.ipf.automaticcarsgame.dto.roadmap.RoadmapRequest;
import com.ipf.automaticcarsgame.dto.roadmap.RoadmapResponse;

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
                RoadmapPosition roadmapPosition= new RoadmapPosition(new Position(i, k), (byte) roadmapRequest.getFields()[i][k]);
                roadmapPosition.setRoadmap(roadmap);
                roadmapPositions.add(roadmapPosition);
            }
        }
        roadmap.setPositions(roadmapPositions);
        return roadmap;
    }

    public static List<RoadmapResponse> map(List<Roadmap> roadmaps) {
        List<RoadmapResponse> roadmapResponses = new ArrayList<>();

        for (Roadmap roadmap : roadmaps) {
            roadmapResponses.add(map(roadmap));
        }

        return roadmapResponses;
    }

    public static RoadmapResponse map(Roadmap roadmap) {
        RoadmapResponse roadmapResponse = new RoadmapResponse();

        roadmapResponse.setRoadmapName(roadmap.getName());

        List<RoadmapPositionDto> positions = mapRoadmapPositionDtos(roadmap);
        roadmapResponse.setRoadmapPositionResponses(positions);

        return roadmapResponse;
    }

    private static List<RoadmapPositionDto> mapRoadmapPositionDtos(Roadmap roadmap) {
        List<RoadmapPositionDto> positions = new ArrayList<>();

        for (RoadmapPosition roadmapPosition : roadmap.getPositions()) {
            RoadmapPositionDto dto= new RoadmapPositionDto();
            dto.setPosition(roadmapPosition.getPosition());
            dto.setValue(roadmapPosition.getValue());

            positions.add(dto);
        }
        return positions;
    }

}
