package com.ipf.automaticcarsgame.mapper;

import com.ipf.automaticcarsgame.domain.Position;
import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.domain.RoadmapPosition;
import com.ipf.automaticcarsgame.service.roadmap.CreateRoadmapRequest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class RoadmapMapperTest {


    public static final String MAP_NAME = "map_name";

    @Test
    public void shouldMapToRoadmap() {
        // given
        final CreateRoadmapRequest createRoadmapRequest = new CreateRoadmapRequest();
        createRoadmapRequest.setName(MAP_NAME);
        createRoadmapRequest.setFields(new int[][]{
                {1},
                {0}
        });

        // when
        final Roadmap roadmap = RoadmapMapper.mapToRoadmap(createRoadmapRequest);

        // then
        assertThat(roadmap.getName()).isEqualTo(MAP_NAME);
        assertThat(roadmap.getPositions()).contains(
                new RoadmapPosition(new Position(0, 0), (byte) 1),
                new RoadmapPosition(new Position(1, 0), (byte) 0)
        );
    }

}