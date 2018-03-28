package com.ipf.automaticcarsgame.rest;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoadmapControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldRemoveMap() {
        final int notUsedMapId = 1;

        final ResponseEntity<String> response = this.restTemplate.exchange(String.format("/api/maps/%s", notUsedMapId), HttpMethod.DELETE, createEmptyBody(), String.class);

        assertThat(JsonPath.<Boolean>read(response.getBody(), "$.success")).isTrue();
    }

    private HttpEntity createEmptyBody() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity(null, httpHeaders);
    }

}