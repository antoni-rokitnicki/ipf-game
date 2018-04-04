package com.ipf.automaticcarsgame.service.game;

import com.ipf.automaticcarsgame.domain.Movement;
import com.ipf.automaticcarsgame.dto.MovementType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class ReturnRouteFinderTest {

    private ReturnRouteFinder returnRouteFinder = new ReturnRouteFinder();

    final List<MovementType> historyMovements;
    final List<MovementType> expectedReturnMovements;


    public ReturnRouteFinderTest(List<MovementType> historyMovements, List<MovementType> expectedReturnMovements) {
        this.historyMovements = historyMovements;
        this.expectedReturnMovements = expectedReturnMovements;
    }

    @Test
    public void shouldReturBackRoute() {
        // given
        final List<Movement> lastMovements = createLastMovementList(this.historyMovements);

        // when
        final List<Movement> returnMovements = returnRouteFinder.findReturnMovements(lastMovements);

        // then
        assertIsEquals(returnMovements, expectedReturnMovements);
    }


    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList(
                        MovementType.FORWARD,
                        MovementType.RIGHT,
                        MovementType.FORWARD
                ), Arrays.asList(
                        MovementType.LEFT,
                        MovementType.LEFT,
                        MovementType.FORWARD,
                        MovementType.LEFT,
                        MovementType.FORWARD,
                        MovementType.LEFT,
                        MovementType.LEFT
                )},

                {Arrays.asList(
                        MovementType.LEFT
                ), Arrays.asList(
                        MovementType.RIGHT
                )},

                {Arrays.asList(
                        MovementType.RIGHT
                ), Arrays.asList(
                        MovementType.LEFT
                )},

                {Arrays.asList(
                        MovementType.LEFT,
                        MovementType.RIGHT
                ), Arrays.asList(
                        MovementType.RIGHT,
                        MovementType.LEFT
                )},

                {Arrays.asList(
                        MovementType.FORWARD
                ), Arrays.asList(
                        MovementType.LEFT,
                        MovementType.LEFT,
                        MovementType.FORWARD,
                        MovementType.LEFT,
                        MovementType.LEFT
                )},

                {Arrays.asList(
                        MovementType.FORWARD,
                        MovementType.FORWARD
                ), Arrays.asList(
                        MovementType.LEFT,
                        MovementType.LEFT,
                        MovementType.FORWARD,
                        MovementType.FORWARD,
                        MovementType.LEFT,
                        MovementType.LEFT
                )},

                {Arrays.asList(
                        MovementType.LEFT,
                        MovementType.FORWARD
                ), Arrays.asList(
                        MovementType.RIGHT,
                        MovementType.LEFT,
                        MovementType.LEFT,
                        MovementType.FORWARD,
                        MovementType.LEFT,
                        MovementType.LEFT
                )}

        });
    }


    private void assertIsEquals(List<Movement> returnMovements, List<MovementType> exprectedList) {
        for (int i = 0; i < returnMovements.size(); i++) {
            assertThat(returnMovements.get(i).getType()).isEqualTo(exprectedList.get(i));
        }

    }

    private List<Movement> createLastMovementList(List<MovementType> movementTypeList) {
        return movementTypeList.stream().map(type -> {
            final Movement move = new Movement();
            move.setType(type);
            return move;
        }).collect(toList());
    }


}