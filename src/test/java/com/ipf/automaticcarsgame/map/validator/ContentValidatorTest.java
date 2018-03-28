package com.ipf.automaticcarsgame.map.validator;

import com.ipf.automaticcarsgame.map.GameMap;
import com.ipf.automaticcarsgame.validator.ValidationResult;
import com.ipf.automaticcarsgame.validator.Validator;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class ContentValidatorTest {

    private Validator validator = new ConnectionValidator();

    @Test
    public void shouldReturnCorrect(){
        final GameMap gameMap = new GameMap();
        gameMap.setFields(new int[][]{{0,1}, {0,1}});

        final ValidationResult validate = validator.validate(gameMap);

        assertThat(validate.isValid()).isTrue();
    }

   /* @Test
    public void shouldReturnInvalidContent(){
        final GameMap gameMap = new GameMap();
        gameMap.setFields(new int[][]{{0,3}, {0,1}});

        final ValidationResult validate = validator.validate(gameMap);

        assertThat(validate.isValid()).isTrue();
    }*/

}