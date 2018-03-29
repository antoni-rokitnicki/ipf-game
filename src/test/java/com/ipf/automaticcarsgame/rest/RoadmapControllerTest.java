package com.ipf.automaticcarsgame.rest;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = BEFORE_CLASS)
public class RoadmapControllerTest {
    private static final String EXISING_ROADMAP_NAME = "Warsaw";
    public static final String NOT_EXISTING_MAP_NAME = "NameNotExistInDB";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldRemoveMap() {
        final String notUsedMapId = EXISING_ROADMAP_NAME;

        final ResponseEntity<String> response = this.restTemplate.exchange(String.format("/api/maps/%s", notUsedMapId), HttpMethod.DELETE, createEmptyBody(), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(JsonPath.<Boolean>read(response.getBody(), "$.success")).isTrue();
    }

    @Test
    public void shouldReturnFalseWhenRemovingNotExistingMap() {
        final String notUsedMapId = NOT_EXISTING_MAP_NAME;

        final ResponseEntity<String> response = this.restTemplate.exchange(String.format("/api/maps/%s", notUsedMapId), HttpMethod.DELETE, createEmptyBody(), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(JsonPath.<Boolean>read(response.getBody(), "$.success")).isFalse();
        assertThat(JsonPath.<String>read(response.getBody(), "$.errors[0].code")).isEqualTo("DOES_NOT_EXIST");
    }

    @Test
    public void shouldSaveMap() throws URISyntaxException, IOException {
        // given
        final HttpEntity<MultiValueMap<String, Object>> entity = createRoadMapRequestEntity(NOT_EXISTING_MAP_NAME, "correct_csv.csv");

        // when
        final ResponseEntity<String> response = restTemplate.exchange("/api/maps", HttpMethod.POST, entity, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(JsonPath.<Boolean>read(response.getBody(), "$.success")).isTrue();
    }


    @Test
    public void shouldReturnErrorAlreadyExists() throws URISyntaxException, IOException {
        // given
        final HttpEntity<MultiValueMap<String, Object>> entity = createRoadMapRequestEntity(EXISING_ROADMAP_NAME, "correct_csv.csv");

        // when
        final ResponseEntity<String> response = restTemplate.exchange("/api/maps", HttpMethod.POST, entity, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(JsonPath.<Boolean>read(response.getBody(), "$.success")).isFalse();
        assertThat(JsonPath.<String>read(response.getBody(), "$.errors[0].code")).isEqualTo("ALREADY_EXISTS");
    }

    @Test
    public void shouldReturnListOfRoadMaps() {
        // given

        // when
        final ResponseEntity<String> response = this.restTemplate.exchange("/api/maps", HttpMethod.GET, createEmptyBody(), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(JsonPath.<Boolean>read(response.getBody(), "$.success")).isTrue();
        assertThat(JsonPath.<JSONArray>read(response.getBody(), "$.data").size()).isGreaterThan(0);
    }

    private HttpEntity<MultiValueMap<String, Object>> createRoadMapRequestEntity(String mapName, String fileName) throws IOException, URISyntaxException {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("name", mapName);
        parameters.add("file", new FileSystemResource(new File(ClassLoader.getSystemResource(fileName).getFile())));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return new HttpEntity<>(parameters, headers);
    }


    private HttpEntity createEmptyBody() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity(null, httpHeaders);
    }

}