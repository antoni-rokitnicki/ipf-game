package com.ipf.automaticcarsgame.mapper;

import com.ipf.automaticcarsgame.dto.roadmap.RoadmapRequest;
import com.ipf.automaticcarsgame.parser.CsvParser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class RoadmapRequestMapper {

    private final CsvParser csvParser;

    public RoadmapRequestMapper(CsvParser csvParser) {
        this.csvParser = csvParser;
    }

    public RoadmapRequest mapToRoadmapRequest(String mapName, InputStream stream) throws IOException {
        final int[][] positions = csvParser.parse(stream);
        final RoadmapRequest createMapRequest = new RoadmapRequest();
        createMapRequest.setName(mapName);
        createMapRequest.setFields(positions);
        return createMapRequest;
    }


}
