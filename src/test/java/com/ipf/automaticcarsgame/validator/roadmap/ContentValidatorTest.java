package com.ipf.automaticcarsgame.validator.roadmap;

import com.ipf.automaticcarsgame.service.roadmap.CreateRoadmapRequest;
import com.ipf.automaticcarsgame.validator.ValidationResult;
import com.ipf.automaticcarsgame.validator.Validator;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class ContentValidatorTest {

    private Validator validator = new ContentValidator();

    @Test
    public void shouldReturnCorrect(){
        final CreateRoadmapRequest gameMap = new CreateRoadmapRequest();
        gameMap.setFields(new int[][]{{0,1}, {0,1}});

        final ValidationResult validate = validator.validate(gameMap);

        assertThat(validate.isValid()).isTrue();
    }

    @Test
    public void shouldReturnInvalidContent(){
        final CreateRoadmapRequest gameMap = new CreateRoadmapRequest();
        gameMap.setFields(new int[][]{{0,3}, {0,1}});

        final ValidationResult validate = validator.validate(gameMap);

        assertThat(validate.isValid()).isFalse();
    }

}