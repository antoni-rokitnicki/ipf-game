package com.ipf.automaticcarsgame.rest;

import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.service.game.GameHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ipf.automaticcarsgame.mapper.ResponseEntityMapper.mapToResponseEntity;

@RestController
@RequestMapping(value = "/api/history", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class GameHistoryController {

    private static final Logger LOG = LoggerFactory.getLogger(GameHistoryController.class);

    private final GameHistoryService gameHistoryService;

    public GameHistoryController(GameHistoryService gameHistoryService) {
        this.gameHistoryService = gameHistoryService;
    }

    @GetMapping
    ResponseEntity<Response<GameHistoryService.HistoryDto>> search(HistoryRequest searchRequest) {
        LOG.info("search, searchRequest: {}", searchRequest);
        return mapToResponseEntity(gameHistoryService.getHistory(searchRequest));
    }

}
