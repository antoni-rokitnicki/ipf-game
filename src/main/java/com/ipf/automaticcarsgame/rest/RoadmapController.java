package com.ipf.automaticcarsgame.rest;


import com.ipf.automaticcarsgame.dto.CreateMapRequest;
import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.dto.ResponseErrorBuilder;
import com.ipf.automaticcarsgame.service.roadmap.RoadmapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/api/maps", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
class RoadmapController {
    private static final Logger LOG = LoggerFactory.getLogger(RoadmapController.class);
    private final RoadmapService roadmapService;

    RoadmapController(RoadmapService roadmapService) {
        this.roadmapService = roadmapService;
    }

    @DeleteMapping(value = "/{name}")
    Response<Void> deleteRoadmap(@PathVariable("name") String name) {
        logInit(name);
        final boolean deleted = roadmapService.deleteRoadmap(name);
        logResult(name, deleted);
        return createResponse(deleted);
    }

    @PostMapping
    Response<Void> createRoadmap(@RequestBody CreateMapRequest createMapRequest) {
        LOG.info("createMap, request: {}", createMapRequest);
        mapToGameMap(createMapRequest);
        return new Response<>();
    }

    private void mapToGameMap(CreateMapRequest createMapRequest) {
        Arrays.stream(createMapRequest.getMap())
                .map(row -> row.trim())
                .map(row -> Arrays.stream(row.split(",")).map(Integer::valueOf).toArray()).toArray();


    }

    private Response<Void> createResponse(boolean deleted) {
        if (!deleted) {
            return new Response<>(ResponseErrorBuilder.responseError().withCode(400).addMessage("Roadmap does not exist").build());
        }
        return new Response<>();
    }

    private void logResult(@PathVariable("name") String name, boolean deleted) {
        LOG.info("deleteRoadmap, name: {}, result: {}", name, deleted);
    }

    private void logInit(@PathVariable("name") String name) {
        LOG.debug("deleteRoadmap init, name: {}", name);
    }

}
