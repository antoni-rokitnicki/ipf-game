package com.ipf.automaticcarsgame.validator.roadmap;

import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.roadmap.RoadmapRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class RoadmapValidatorProcessorTest {

    private RoadmapValidatorProcessor roadmapValidatorProcessor;

    private RoadmapValidator roadmapValidator = mock(RoadmapValidator.class);

    @Before
    public void createRoadmapValidatorProcessor() {
        roadmapValidatorProcessor = new RoadmapValidatorProcessor(Arrays.asList(roadmapValidator));
    }


    @Test
    public void shouldReturnSuccess() throws Exception {
        // given
        when(roadmapValidator.validate(any())).thenReturn(createCorrectResult());

        // when
        final Result result = roadmapValidatorProcessor.validate(new RoadmapRequest());

        // then
        assertThat(result.isValid()).isTrue();
    }

    @Test
    public void shouldReturnError() throws Exception {
        // given
        when(roadmapValidator.validate(any())).thenReturn(createIncorrectResult());

        // when
        final Result result = roadmapValidatorProcessor.validate(new RoadmapRequest());

        // then
        assertThat(result.isValid()).isFalse();
        assertThat(result.getErrors().size()).isGreaterThan(0);
    }

    private Result createCorrectResult() {
        return new Result();
    }


    private Result createIncorrectResult() {
        final Result result = new Result();
        result.addError(new Result.Error("CODE", "Error message"));
        return result;
    }


}