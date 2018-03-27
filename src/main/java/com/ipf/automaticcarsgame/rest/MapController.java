package com.ipf.automaticcarsgame.rest;


import com.ipf.automaticcarsgame.dto.CreateMapRequest;
import com.ipf.automaticcarsgame.dto.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/map", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
class MapController {
    private static final Logger LOG = LoggerFactory.getLogger(MapController.class);

    @DeleteMapping(value = "/{id}")
    public Response<Void> deleteMap(@PathVariable("id") int id) {
        LOG.info("deleteMap, id: {}", id);
        return new Response<>();
    }

    @PostMapping
    public Response<Void> createMap(@RequestBody CreateMapRequest createMapRequest) {
        LOG.info("createMap, request: {}", createMapRequest);
        return new Response<>();
    }

}
