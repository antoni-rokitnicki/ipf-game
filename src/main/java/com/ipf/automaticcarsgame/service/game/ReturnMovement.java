package com.ipf.automaticcarsgame.service.game;


import com.ipf.automaticcarsgame.domain.Movement;
import com.ipf.automaticcarsgame.dto.MovementType;

import java.util.ArrayList;
import java.util.List;

public class ReturnMovement {

    public List<Movement> findReturnMovements(List<Movement> lastMovementsList) {
        final List<Movement> returnMovements = new ArrayList<>();
        for (int i = 0; i < lastMovementsList.size(); i++) {
            final Movement movement = lastMovementsList.get(i);
            if (i == 0 && MovementType.FORWARD.equals(movement.getType())) {
                addMovement(returnMovements, MovementType.LEFT);
                addMovement(returnMovements, MovementType.LEFT);
                addMovement(returnMovements, MovementType.FORWARD);
            } else if (MovementType.LEFT.equals(movement.getType())) {
                addMovement(returnMovements, MovementType.RIGHT);
            } else if (MovementType.RIGHT.equals(movement.getType())) {
                addMovement(returnMovements, MovementType.LEFT);
            } else if (i == lastMovementsList.size() - 1 && MovementType.FORWARD.equals(movement.getType())) {
                addMovement(returnMovements, MovementType.FORWARD);
                addMovement(returnMovements, MovementType.LEFT);
                addMovement(returnMovements, MovementType.LEFT);
            }

        }
        return returnMovements;
    }

    private void addMovement(List<Movement> returnMovements, MovementType type) {
        final Movement moveForward = new Movement();
        moveForward.setType(type);
        returnMovements.add(moveForward);
    }

}
