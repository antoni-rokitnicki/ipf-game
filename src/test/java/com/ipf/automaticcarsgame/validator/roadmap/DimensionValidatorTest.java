package com.ipf.automaticcarsgame.validator.roadmap;

import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.roadmap.RoadmapRequest;
import com.ipf.automaticcarsgame.validator.Validator;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DimensionValidatorTest {

    private Validator validator = new DimensionValidator();

    @Test
    public void shouldReturnSuccess() {
        // given
        final RoadmapRequest correctRoadmap = createCorrectRoadmap();

        // when
        final Result validate = validator.validate(correctRoadmap);

        // then
        assertThat(validate.isValid()).isTrue();
    }


    @Test
    public void shouldReturnIncorrectDimensionError() {
        // given
        final RoadmapRequest incorrectRoadmap = createIncorrectRoadmap();

        // when
        final Result validate = validator.validate(incorrectRoadmap);

        // then
        assertThat(validate.isValid()).isFalse();
        assertThat(validate.getErrors().get(0).getCode()).isEqualTo("INCORRECT_DIMENSION");
    }

    @Test
    public void shouldReturnIncorrectDimensionErrorWhenEmpty() {
        // given
        final RoadmapRequest emptyRoadmap = createEmptyRoadmap();

        // when
        final Result validate = validator.validate(emptyRoadmap);

        // then
        assertThat(validate.isValid()).isFalse();
        assertThat(validate.getErrors().get(0).getCode()).isEqualTo("INCORRECT_DIMENSION");
    }

    private RoadmapRequest createIncorrectRoadmap() {
        final RoadmapRequest incorrectRoadmap = new RoadmapRequest();
        incorrectRoadmap.setFields(new int[][]{
                {0, 1},
                {0,}
        });
        return incorrectRoadmap;
    }

    private RoadmapRequest createEmptyRoadmap() {
        final RoadmapRequest incorrectRoadmap = new RoadmapRequest();
        incorrectRoadmap.setFields(new int[][]{});
        return incorrectRoadmap;
    }


    private RoadmapRequest createCorrectRoadmap() {
        final RoadmapRequest correctRoadmap = new RoadmapRequest();
        correctRoadmap.setFields(new int[][]{
                {0, 1},
                {0, 0}
        });
        return correctRoadmap;
    }


}