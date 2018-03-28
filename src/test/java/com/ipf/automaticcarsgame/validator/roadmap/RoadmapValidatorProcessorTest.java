package com.ipf.automaticcarsgame.validator.roadmap;

import com.ipf.automaticcarsgame.service.roadmap.CreateRoadmapRequest;
import com.ipf.automaticcarsgame.validator.ValidationResult;
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
        final ValidationResult result = roadmapValidatorProcessor.validate(new CreateRoadmapRequest());

        // then
        assertThat(result.isValid()).isTrue();
    }

    @Test
    public void shouldReturnError() throws Exception {
        // given
        when(roadmapValidator.validate(any())).thenReturn(createIncorrectResult());

        // when
        final ValidationResult result = roadmapValidatorProcessor.validate(new CreateRoadmapRequest());

        // then
        assertThat(result.isValid()).isFalse();
        assertThat(result.getErrors().size()).isGreaterThan(0);
    }

    private ValidationResult createCorrectResult() {
        return new ValidationResult();
    }


    private ValidationResult createIncorrectResult() {
        final ValidationResult validationResult = new ValidationResult();
        validationResult.addError(new ValidationResult.Error("CODE", "Error message"));
        return validationResult;
    }


}