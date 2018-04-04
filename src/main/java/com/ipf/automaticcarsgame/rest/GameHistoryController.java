package com.ipf.automaticcarsgame.rest;

import com.ipf.automaticcarsgame.dto.HistoryDto;
import com.ipf.automaticcarsgame.dto.HistoryRequest;
import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.service.game.GameHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ipf.automaticcarsgame.mapper.ResponseEntityMapper.mapToResponseEntity;

@RestController
@RequestMapping(value = "/api/history")
@Api(tags = {"Game History Services"}, description = "Search historic games")
class GameHistoryController {

    private static final Logger LOG = LoggerFactory.getLogger(GameHistoryController.class);

    private final GameHistoryService gameHistoryService;

    GameHistoryController(GameHistoryService gameHistoryService) {
        this.gameHistoryService = gameHistoryService;
    }

    @ApiOperation(value = "Search historic games")
    @GetMapping
    ResponseEntity<Response<HistoryDto>> search(HistoryRequest searchRequest) {
        LOG.info("search, searchRequest: {}", searchRequest);
        return mapToResponseEntity(gameHistoryService.getHistory(searchRequest));
    }

}
