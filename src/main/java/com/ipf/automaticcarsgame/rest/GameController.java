package com.ipf.automaticcarsgame.rest;

import com.ipf.automaticcarsgame.dto.game.GameRequest;
import com.ipf.automaticcarsgame.mapper.ResponseMapper;
import com.ipf.automaticcarsgame.service.game.GameService;
import com.ipf.automaticcarsgame.validator.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/games", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {

    private static final Logger LOG = LoggerFactory.getLogger(GameController.class);

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    ResponseEntity<Object> createGame(@RequestBody GameRequest gameRequest) {
        LOG.info("create game, request: {}", gameRequest);

        ValidationResult validationResult = gameService.createGame(gameRequest);
        return ResponseMapper.map(validationResult);
    }
}
