package com.ipf.automaticcarsgame.rest;

import com.ipf.automaticcarsgame.dto.game.GameCarRequest;
import com.ipf.automaticcarsgame.dto.game.GameRequest;
import com.ipf.automaticcarsgame.mapper.ResponseMapper;
import com.ipf.automaticcarsgame.service.game.GameCarService;
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
    private final GameCarService gameCarService;

    public GameController(GameService gameService, GameCarService gameCarService) {
        this.gameService = gameService;
        this.gameCarService = gameCarService;
    }

    @PostMapping
    ResponseEntity<Object> createGame(@RequestBody GameRequest gameRequest) {
        LOG.info("create game, request: {}", gameRequest);

        ValidationResult validationResult = gameService.createGame(gameRequest);
        return ResponseMapper.map(validationResult);
    }

    @PostMapping("/cars")
    ResponseEntity<Object> addCarToGame(@RequestBody GameCarRequest gameCarRequest) {
        LOG.info("add car to game, request: {}", gameCarRequest);

        ValidationResult validationResult = gameCarService.addCarToGame(gameCarRequest);
        return ResponseMapper.map(validationResult);
    }
}
