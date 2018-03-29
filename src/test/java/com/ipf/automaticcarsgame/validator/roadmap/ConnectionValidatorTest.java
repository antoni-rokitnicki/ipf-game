package com.ipf.automaticcarsgame.validator.roadmap;

import com.ipf.automaticcarsgame.dto.Result;
import com.ipf.automaticcarsgame.dto.roadmap.RoadmapRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ConnectionValidatorTest {
    private ConnectionValidator connectionValidator = new ConnectionValidator();

    private int[][] mapPoints;
    private boolean expectedResult;

    public ConnectionValidatorTest(int[][] mapPoints, boolean expectedResult) {
        this.mapPoints = mapPoints;
        this.expectedResult = expectedResult;
    }

    @Test
    public void shouldReturnErrorWhenManyRoads() {
        // given
        final RoadmapRequest gameMap = new RoadmapRequest();
        gameMap.setFields(mapPoints);

        // when
        final Result validate = connectionValidator.validate(gameMap);

        // then
        assertThat(validate.isValid()).isEqualTo(expectedResult);

    }


    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[][]{
                        {0, 0, 0},
                        {1, 0, 0},
                        {1, 1, 1}
                }, true},
                {new int[][]{
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 1}
                }, true},
                {new int[][]{
                        {1, 0, 1},
                        {1, 0, 1},
                        {1, 1, 1}
                }, true},
                {new int[][]{
                        {0, 0, 1, 0},
                        {1, 1, 1, 0},
                        {1, 0, 1, 0},
                        {1, 1, 1, 0}
                }, true},
                {new int[][]{
                        {1, 0, 0},
                        {1, 0, 0},
                        {1, 0, 1}
                }, false},
                {new int[][]{
                        {1, 0, 1},
                        {0, 1, 0},
                        {1, 0, 1}
                }, false}
        });
    }


}