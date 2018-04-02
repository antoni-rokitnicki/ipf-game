package com.ipf.automaticcarsgame.service.game;

import com.ipf.automaticcarsgame.domain.Movement;
import com.ipf.automaticcarsgame.dto.MovementType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ReturnMovementTest {

    private ReturnMovement returnMovement = new ReturnMovement();


    @Test
    public void shouldReturBackRoute() {
        final List<Movement> movements = createLastMovementList(Arrays.asList(
                MovementType.LEFT
        ));

        final List<Movement> returnMovements = returnMovement.findReturnMovements(movements);

        assertIsEquals(returnMovements, Arrays.asList(
                MovementType.RIGHT
        ));
    }

    @Test
    public void shouldReturBackRoutee() {
        final List<Movement> movements = createLastMovementList(Arrays.asList(
                MovementType.LEFT,
                MovementType.RIGHT
        ));

        final List<Movement> returnMovements = returnMovement.findReturnMovements(movements);

        assertIsEquals(returnMovements, Arrays.asList(
                MovementType.RIGHT,
                MovementType.LEFT
        ));
    }


    @Test
    public void shouldReturBackRoute2() {
        final List<Movement> movements = createLastMovementList(Arrays.asList(
                MovementType.FORWARD
        ));

        final List<Movement> returnMovements = returnMovement.findReturnMovements(movements);

        assertIsEquals(returnMovements, Arrays.asList(
                MovementType.LEFT,
                MovementType.LEFT,
                MovementType.FORWARD
        ));
    }

    @Test
    public void shouldReturBackRoute3() {
        final List<Movement> movements = createLastMovementList(Arrays.asList(
                MovementType.FORWARD,
                MovementType.FORWARD
        ));

        final List<Movement> returnMovements = returnMovement.findReturnMovements(movements);

        assertIsEquals(returnMovements, Arrays.asList(
                MovementType.LEFT,
                MovementType.LEFT,
                MovementType.FORWARD,
                MovementType.FORWARD,
                MovementType.LEFT,
                MovementType.LEFT
        ));
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