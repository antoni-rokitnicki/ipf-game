package com.ipf.automaticcarsgame.rest;


import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.dto.ResponseErrorBuilder;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.roadmap.RoadmapRequest;
import com.ipf.automaticcarsgame.mapper.RoadmapRequestMapper;
import com.ipf.automaticcarsgame.service.roadmap.RoadmapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

import static com.ipf.automaticcarsgame.mapper.ResponseEntityMapper.mapToResponseEntity;

@RestController
@RequestMapping(value = "/api/maps", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
class RoadmapController {
    private static final Logger LOG = LoggerFactory.getLogger(RoadmapController.class);
    private final RoadmapService roadmapService;
    private final RoadmapRequestMapper roadmapRequestMapper;


    RoadmapController(RoadmapService roadmapService, RoadmapRequestMapper roadmapRequestMapper) {
        this.roadmapService = roadmapService;
        this.roadmapRequestMapper = roadmapRequestMapper;
    }

    @DeleteMapping(value = "/{name}")
    ResponseEntity<Response<Void>> deleteRoadmap(@PathVariable("name") String name) throws UnsupportedEncodingException {
        final String decodeName = URLDecoder.decode(name, "UTF-8");
        logDeleteInit(decodeName);
        final Result result = roadmapService.deleteRoadmap(decodeName);
        logDeleteResult(decodeName, result);
        return mapToResponseEntity(result);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Response<Void>> createRoadmap(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) throws IOException {
        logCreateRoadmapInit(name, file);
        final RoadmapRequest roadmapRequest = roadmapRequestMapper.mapToRoadmapRequest(name, file.getInputStream());
        final Result result = this.roadmapService.createRoadmap(roadmapRequest);
        return mapToResponseEntity(result);
    }

    @GetMapping
    ResponseEntity<Response<List<Roadmap>>> findAll() {
        logFindAll();
        return mapToResponseEntity(roadmapService.findAll());
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

    private void logCreateRoadmapInit(String name, MultipartFile file) {
        LOG.info("createRoadmap, name: {}, fileName: {}", name, file.getOriginalFilename());
    }

    private void logDeleteResult(String name, Result result) {
        LOG.info("deleteRoadmap, name: {}, result: {}", name, result.isValid());
    }

    private void logDeleteInit(String name) {
        LOG.debug("deleteRoadmap init, name: {}", name);
    }

    private void logFindAll() {
        LOG.info("findAll");
    }


}
