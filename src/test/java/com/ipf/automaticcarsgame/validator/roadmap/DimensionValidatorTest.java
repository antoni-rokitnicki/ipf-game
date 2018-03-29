package com.ipf.automaticcarsgame.validator.roadmap;

import com.ipf.automaticcarsgame.service.roadmap.CreateRoadmapRequest;
import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.validator.Validator;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DimensionValidatorTest {

    private Validator validator = new DimensionValidator();

    @Test
    public void shouldReturnSuccess() {
        // given
        final CreateRoadmapRequest correctRoadmap = createCorrectRoadmap();

        // when
        final Result validate = validator.validate(correctRoadmap);

        // then
        assertThat(validate.isValid()).isTrue();
    }


    @Test
    public void shouldReturnIncorrectDimensionError() {
        // given
        final CreateRoadmapRequest incorrectRoadmap = createIncorrectRoadmap();

        // when
        final Result validate = validator.validate(incorrectRoadmap);

        // then
        assertThat(validate.isValid()).isFalse();
        assertThat(validate.getErrors().get(0).getCode()).isEqualTo("INCORRECT_DIMENSION");
    }

    @Test
    public void shouldReturnIncorrectDimensionErrorWhenEmpty() {
        // given
        final CreateRoadmapRequest emptyRoadmap = createEmptyRoadmap();

        // when
        final Result validate = validator.validate(emptyRoadmap);

        // then
        assertThat(validate.isValid()).isFalse();
        assertThat(validate.getErrors().get(0).getCode()).isEqualTo("INCORRECT_DIMENSION");
    }

    private CreateRoadmapRequest createIncorrectRoadmap() {
        final CreateRoadmapRequest incorrectRoadmap = new CreateRoadmapRequest();
        incorrectRoadmap.setFields(new int[][]{
                {0, 1},
                {0,}
        });
        return incorrectRoadmap;
    }

    private CreateRoadmapRequest createEmptyRoadmap() {
        final CreateRoadmapRequest incorrectRoadmap = new CreateRoadmapRequest();
        incorrectRoadmap.setFields(new int[][]{});
        return incorrectRoadmap;
    }


    private CreateRoadmapRequest createCorrectRoadmap() {
        final CreateRoadmapRequest correctRoadmap = new CreateRoadmapRequest();
        correctRoadmap.setFields(new int[][]{
                {0, 1},
                {0, 0}
        });
        return correctRoadmap;
    }


}