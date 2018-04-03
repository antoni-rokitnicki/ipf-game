package com.ipf.automaticcarsgame.rest;

import com.ipf.automaticcarsgame.domain.Game;
import com.ipf.automaticcarsgame.dto.MovementType;
import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.ReturnCar;
import com.ipf.automaticcarsgame.dto.game.GameCarRequest;
import com.ipf.automaticcarsgame.dto.game.GameRequest;
import com.ipf.automaticcarsgame.service.game.GameCarService;
import com.ipf.automaticcarsgame.service.game.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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


    @DeleteMapping("/cars")
    ResponseEntity<Response<Void>> removeCarFromGame(@RequestBody GameCarRequest gameCarRequest){
        LOG.info("remove car from game, request: {}", gameCarRequest);

        Result result = gameCarService.removeCarFromGame(gameCarRequest);
        return mapToResponseEntity(result);
    }

    @GetMapping("/{mapName}")
    ResponseEntity<Response<Optional<Game>>> getActiveGame(@PathVariable("mapName") String mapName) {
        LOG.info("getActiveGame, id: {}", mapName);
        final Optional<Game> game = gameService.getActiveGameByMapName(mapName);
        return mapToResponseEntity(game);
    }

    @PutMapping("/{gameId}/{carName}/return")
    ResponseEntity<Response<Optional<Game>>> returnCar(@PathVariable("gameId") Integer gameId, @PathVariable("carName") String carName, @RequestBody ReturnCar returnCar) throws UnsupportedEncodingException {
        final String decodeCarName = URLDecoder.decode(carName, "UTF-8");
        LOG.info("returnCar, gameId: {}, carName: {}, returnCar: {}", gameId, decodeCarName, returnCar);
        final Optional<Game> game = gameService.returnCar(gameId, decodeCarName, returnCar.getNoOfMovements());
        return mapToResponseEntity(game);
    }




    @PutMapping("/moveCar/{carName}")
    ResponseEntity<Response<Void>> moveCar(@PathVariable("carName") String carName) throws UnsupportedEncodingException {
        final String decodeCarName = URLDecoder.decode(carName, "UTF-8");
        LOG.info("moveCar,  carName: {}",  decodeCarName);
        final Result result = gameService.moveCar(carName, MovementType.LEFT, 1);
        return mapToResponseEntity(result);
    }

}
