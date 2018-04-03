package com.ipf.automaticcarsgame.rest;

import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.ipf.automaticcarsgame.dto.car.CarResponse;
import com.ipf.automaticcarsgame.service.car.CarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import static com.ipf.automaticcarsgame.mapper.ResponseEntityMapper.mapToResponseEntity;


@RestController
@RequestMapping(value = "/api/cars", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Car Services"}, description = "Add, remove, find, repair car")
public class CarController {

    private static final Logger LOG = LoggerFactory.getLogger(CarController.class);

    private final CarService carService;

    CarController(CarService carService) {
        this.carService = carService;
    }

    @ApiOperation(value = "Add a car to the persistent store")
    @PostMapping
    ResponseEntity<Response<Void>> createCar(@RequestBody CarRequest carRequest) {
        LOG.info("create car, request: {}", carRequest);

        Result result = carService.createCar(carRequest);
        return mapToResponseEntity(result);
    }

    @ApiOperation(value = "Find all available cars")
    @GetMapping(consumes = MediaType.ALL_VALUE)
    ResponseEntity<Response<List<CarResponse>>> findAll() {
        LOG.info("find all cars");
        List<CarResponse> cars = carService.findAll();
        return mapToResponseEntity(cars);
    }

    @ApiOperation(value = "Remove a car from the persistent store")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "carName", required = true, dataType = "String", paramType = "path")
    })
    @DeleteMapping(value = "/{name}", consumes = MediaType.ALL_VALUE)
    ResponseEntity<Response<Void>> removeCar(@PathVariable("name") String carName) throws UnsupportedEncodingException {
        final String decodeCarName = urlDecode(carName);
        LOG.info("remove car, carName: {}", decodeCarName);

        Result result = carService.removeCar(decodeCarName);

        return mapToResponseEntity(result);
    }

    @ApiOperation(value = "Repair crashed car")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "carName", required = true, dataType = "String", paramType = "path")
    })
    @PutMapping(value = "/{name}/repair", consumes = MediaType.ALL_VALUE)
    ResponseEntity<Response<Void>> repairCar(@PathVariable("name") String carName) throws UnsupportedEncodingException {
        final String decodeCarName = urlDecode(carName);
        LOG.info("repair car car, carName: {}", decodeCarName);

        final Result result = carService.repairCar(decodeCarName);

        return mapToResponseEntity(result);
    }

    private String urlDecode(@PathVariable("name") String carName) throws UnsupportedEncodingException {
        return URLDecoder.decode(carName, "UTF-8");
    }


}
