package com.ipf.automaticcarsgame.service.game;


import com.ipf.automaticcarsgame.domain.Movement;
import com.ipf.automaticcarsgame.dto.MovementType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReturnMovement {

    public List<Movement> findReturnMovements(List<Movement> lastMovementsList) {
        final List<Movement> returnMovements = new ArrayList<>();
        boolean turnedInReturnDirection = false;
        for (int i = 0; i < lastMovementsList.size(); i++) {
            final Movement movement = lastMovementsList.get(i);

            if (MovementType.FORWARD.equals(movement.getType())) {
                if (turnedInReturnDirection) {
                    addMovement(returnMovements, MovementType.FORWARD);
                } else {
                    addMovement(returnMovements, MovementType.LEFT);
                    addMovement(returnMovements, MovementType.LEFT);
                    addMovement(returnMovements, MovementType.FORWARD);
                    turnedInReturnDirection = true;
                }
                if (isLastMovement(lastMovementsList, i)) {
                    addMovement(returnMovements, MovementType.LEFT);
                    addMovement(returnMovements, MovementType.LEFT);
                }
            } else if (MovementType.LEFT.equals(movement.getType())) {
                addMovement(returnMovements, MovementType.RIGHT);
            } else if (MovementType.RIGHT.equals(movement.getType())) {
                addMovement(returnMovements, MovementType.LEFT);
            }

        }
        return returnMovements;
    }

    private boolean isLastMovement(List<Movement> lastMovementsList, int i) {
        return i == lastMovementsList.size() - 1;
    }

    private void addMovement(List<Movement> returnMovements, MovementType type) {
        final Movement moveForward = new Movement();
        moveForward.setType(type);
        returnMovements.add(moveForward);
    }

}
