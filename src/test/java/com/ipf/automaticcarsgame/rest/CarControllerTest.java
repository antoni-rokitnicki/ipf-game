package com.ipf.automaticcarsgame.rest;

import com.ipf.automaticcarsgame.dto.car.CarRequest;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = BEFORE_CLASS)
public class CarControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldRepairCar() {
        final String crashedCar = "bmw";

        final ResponseEntity<String> response = this.restTemplate.exchange("/api/cars", HttpMethod.PUT, createRepairCarBody(crashedCar), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturnErrorWhenCarDoesNotExist() {
        final String carDoesNotExist = "car_does_not_exist";

        final ResponseEntity<String> response = this.restTemplate.exchange("/api/cars", HttpMethod.PUT, createRepairCarBody(carDoesNotExist), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(JsonPath.<Boolean>read(response.getBody(), "$.success")).isFalse();
        assertThat(JsonPath.<String>read(response.getBody(), "$.errors[0].code")).isEqualTo("DOES_NOT_EXIST");
    }

    private HttpEntity createRepairCarBody(String carName) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        final CarRequest carRequest = new CarRequest();
        carRequest.setCrashed(false);
        carRequest.setName(carName);
        return new HttpEntity(carRequest, httpHeaders);
    }

}