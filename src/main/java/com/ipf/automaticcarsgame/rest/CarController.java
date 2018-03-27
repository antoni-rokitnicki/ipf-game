package com.ipf.automaticcarsgame.rest;

import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.dto.car.CarRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/cars", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CarController {

    private static final Logger LOG = LoggerFactory.getLogger(CarController.class);

    @PostMapping
    public Response<Void> createCar(@RequestBody CarRequest carRequest){
        LOG.info("create car, request: {}", carRequest);
        return new Response<>();
    }

    @GetMapping
    public Response<Void> findAll(){
        LOG.info("find all cars");
        return new Response<>();
    }

    @DeleteMapping
    public Response<Void> removeCar(@RequestBody CarRequest carRequest){
        LOG.info("remove car, request: {}", carRequest);
        return new Response<>();
    }

}
