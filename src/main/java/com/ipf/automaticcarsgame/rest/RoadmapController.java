package com.ipf.automaticcarsgame.rest;


import com.ipf.automaticcarsgame.csvparser.CsvParser;
import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.dto.ResponseError;
import com.ipf.automaticcarsgame.dto.ResponseErrorBuilder;
import com.ipf.automaticcarsgame.service.roadmap.CreateRoadmapRequest;
import com.ipf.automaticcarsgame.service.roadmap.RoadmapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping(value = "/api/maps", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
class RoadmapController {
    private static final Logger LOG = LoggerFactory.getLogger(RoadmapController.class);
    private final RoadmapService roadmapService;
    private final CsvParser csvParser;

    RoadmapController(RoadmapService roadmapService, CsvParser csvParser) {
        this.roadmapService = roadmapService;
        this.csvParser = csvParser;
    }

    @DeleteMapping(value = "/{name}")
    Response<Void> deleteRoadmap(@PathVariable("name") String name) {
        logInit(name);
        final boolean deleted = roadmapService.deleteRoadmap(name);
        logResult(name, deleted);
        return createResponse(deleted);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Response<Void> createRoadmap(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) throws IOException {
        LOG.info("createRoadmap, name: {}, fileName: {}", name, file.getOriginalFilename());
        final CreateRoadmapRequest createMapRequest = mapToCreateRoadmapRequest(name, file);
        this.roadmapService.createRoadmap(createMapRequest);
        return new Response<>();
    }

    private CreateRoadmapRequest mapToCreateRoadmapRequest(@RequestParam("name") String mapName, MultipartFile file) throws IOException {
        final int[][] positions = csvParser.pareseCsv(file.getInputStream());
        final CreateRoadmapRequest createMapRequest = new CreateRoadmapRequest();
        createMapRequest.setName(mapName);
        createMapRequest.setFields(positions);
        return createMapRequest;
    }

    private Response<Void> createResponse(boolean deleted) {
        if (!deleted) {
            Response<Void> response = new Response<>();
            ResponseError responseError = ResponseErrorBuilder.builder().withCode("ROADMAP_NOT_EXIST").withMessage("Roadmap does not exist").build();
            response.addError(responseError);
            return response;
        }
        return new Response<>();
    }

    private void logResult(@PathVariable("name") String name, boolean deleted) {
        LOG.info("deleteRoadmap, name: {}, result: {}", name, deleted);
    }

    private void logInit(@PathVariable("name") String name) {
        LOG.debug("deleteRoadmap init, name: {}", name);
    }

    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<Response<Void>> handleCsvException(NumberFormatException ex) {
        LOG.warn("Invalid csv format", ex);
        return ResponseEntity.badRequest().body(new Response<>(Arrays.asList(ResponseErrorBuilder.builder().withCode("INVALID_FORMAT").withMessage("Invalid format").build())));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response<Void>> handleException(Exception ex) {
        LOG.error("Internal Server Error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(Arrays.asList(ResponseErrorBuilder.builder().withCode("INTERNAL_SERVER_ERROR").withMessage("Internal server error").build())));
    }

}
