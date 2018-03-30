package com.ipf.automaticcarsgame.rest;

import com.ipf.automaticcarsgame.domain.Game;
import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.game.GameCarRequest;
import com.ipf.automaticcarsgame.dto.game.GameRequest;
import com.ipf.automaticcarsgame.service.game.GameCarService;
import com.ipf.automaticcarsgame.service.game.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.ipf.automaticcarsgame.mapper.ResponseEntityMapper.mapToResponseEntity;

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
    ResponseEntity<Response<Void>> createGame(@RequestBody GameRequest gameRequest) {
        LOG.info("create game, request: {}", gameRequest);

        Result result = gameService.createGame(gameRequest);
        return mapToResponseEntity(result);
    }

    @PostMapping("/cars")
    ResponseEntity<Response<Void>> addCarToGame(@RequestBody GameCarRequest gameCarRequest) {
        LOG.info("add car to game, request: {}", gameCarRequest);

        Result result = gameCarService.addCarToGame(gameCarRequest);
        return mapToResponseEntity(result);
    }

    @GetMapping("/{mapName}")
    ResponseEntity<Response<Optional<Game>>> getActiveGame(@PathVariable("mapName") String mapName) {
        LOG.info("getActiveGame, id: {}", mapName);
        final Optional<Game> game = gameService.getActiveGameByMapName(mapName);
        return mapToResponseEntity(game);
    }

}
