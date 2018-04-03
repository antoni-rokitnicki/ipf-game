package com.ipf.automaticcarsgame.rest;

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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
    public void shouldRepairCar() throws UnsupportedEncodingException {
        final String crashedCar = URLEncoder.encode("bmw x/4", "UTF-8");

        final ResponseEntity<String> response = this.restTemplate.exchange(String.format("/api/cars/%s/repair", crashedCar), HttpMethod.PUT, createRepairCarBody(), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturnErrorWhenCarDoesNotExist() throws UnsupportedEncodingException {
        final String carDoesNotExist = URLEncoder.encode("car does not exist", "UTF-8");

        final ResponseEntity<String> response = this.restTemplate.exchange(String.format("/api/cars/%s/repair", carDoesNotExist), HttpMethod.PUT, createRepairCarBody(), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(JsonPath.<Boolean>read(response.getBody(), "$.success")).isFalse();
        assertThat(JsonPath.<String>read(response.getBody(), "$.errors[0].code")).isEqualTo("DOES_NOT_EXIST");
    }

    private HttpEntity createRepairCarBody() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity(null, httpHeaders);
    }

}