package com.ipf.automaticcarsgame.rest;

import com.ipf.automaticcarsgame.domain.Game;
import com.ipf.automaticcarsgame.dto.MovementType;
import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.ReturnCar;
import com.ipf.automaticcarsgame.dto.game.GameCarRequest;
import com.ipf.automaticcarsgame.dto.game.GameRequest;
import com.ipf.automaticcarsgame.dto.game.RemoveGameCarRequest;
import com.ipf.automaticcarsgame.service.game.GameCarService;
import com.ipf.automaticcarsgame.service.game.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"Game Services"}, description = "Create game, add car to game, remove car from game, get active game, return car")
public class GameController {

    private static final Logger LOG = LoggerFactory.getLogger(GameController.class);

    private final GameService gameService;
    private final GameCarService gameCarService;

    public GameController(GameService gameService, GameCarService gameCarService) {
        this.gameService = gameService;
        this.gameCarService = gameCarService;
    }

    @ApiOperation(value = "Create game")
    @PostMapping
    ResponseEntity<Response<Void>> createGame(@RequestBody GameRequest gameRequest) {
        LOG.info("create game, request: {}", gameRequest);

        Result result = gameService.createGame(gameRequest);
        return mapToResponseEntity(result);
    }

    @ApiOperation(value = "Add car to game")
    @PostMapping("/cars")
    ResponseEntity<Response<Void>> addCarToGame(@RequestBody GameCarRequest gameCarRequest) {
        LOG.info("add car to game, request: {}", gameCarRequest);

        Result result = gameCarService.addCarToGame(gameCarRequest);
        return mapToResponseEntity(result);
    }

    @ApiOperation(value = "Remove car from game")
    @DeleteMapping("/cars")
    ResponseEntity<Response<Void>> removeCarFromGame(@RequestBody RemoveGameCarRequest gameCarRequest){
        LOG.info("remove car from game, request: {}", gameCarRequest);

        Result result = gameCarService.removeCarFromGame(gameCarRequest);
        return mapToResponseEntity(result);
    }

    @ApiOperation(value = "Get active game by map's name")
    @GetMapping(name = "/{mapName}", consumes = MediaType.ALL_VALUE)
    ResponseEntity<Response<Optional<Game>>> getActiveGame(@PathVariable("mapName") String mapName) {
        LOG.info("getActiveGame, id: {}", mapName);
        final Optional<Game> game = gameService.getActiveGameByMapName(mapName);
        return mapToResponseEntity(game);
    }

    @ApiOperation(value = "Return car")
    @PutMapping("/{carName}/return")
    ResponseEntity<Response<Optional<Game>>> returnCar(@PathVariable("carName") String carName, @RequestBody ReturnCar returnCar) throws UnsupportedEncodingException {
        final String decodeCarName = URLDecoder.decode(carName, "UTF-8");
        LOG.info("returnCar, carName: {}, returnCar: {}", decodeCarName, returnCar);
        final Optional<Game> game = gameService.returnCar(decodeCarName, returnCar.getNoOfMovements());
        return mapToResponseEntity(game);
    }


    @ApiOperation(value = "Move car")
    @PutMapping("/{carName}/{direction}/{nrOfSteps}")
    ResponseEntity<Response<Void>> moveCar(@PathVariable("carName") String carName, @PathVariable("direction") MovementType direction, @PathVariable(required = false, name = "nrOfSteps") Integer nrOfSteps) throws UnsupportedEncodingException {
        final String decodeCarName = URLDecoder.decode(carName, "UTF-8");
        LOG.info("moveCar,  carName: {}", decodeCarName);
        final Result result = gameService.moveCar(decodeCarName, direction, nrOfSteps);
        return mapToResponseEntity(result);
    }

}
