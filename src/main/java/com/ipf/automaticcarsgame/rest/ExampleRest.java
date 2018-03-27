package com.ipf.automaticcarsgame.rest;


import com.ipf.automaticcarsgame.dto.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/example"/*, consumes = MediaType.APPLICATION_JSON_VALUE, */, produces = MediaType.APPLICATION_JSON_VALUE)
class ExampleRest {

    @GetMapping
    public Response<String> example() {
        return new Response<>("OK");
    }

}
