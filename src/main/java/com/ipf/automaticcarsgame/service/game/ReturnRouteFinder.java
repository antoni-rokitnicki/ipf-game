package com.ipf.automaticcarsgame.service.game;


import com.ipf.automaticcarsgame.domain.Movement;
import com.ipf.automaticcarsgame.dto.MovementType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReturnRouteFinder {

    public List<Movement> findReturnMovements(List<Movement> lastMovementsList) {
        final List<Movement> returnMovements = new ArrayList<>();
        boolean turnedInReturnDirection = false;
        for (int i = 0; i < lastMovementsList.size(); i++) {
            final Movement movement = lastMovementsList.get(i);

            if (MovementType.FORWARD.equals(movement.getType())) {
                if (turnedInReturnDirection) {
                    addMovement(returnMovements, MovementType.FORWARD, movement.getNrOfMovements());
                } else {
                    addMovement(returnMovements, MovementType.LEFT, movement.getNrOfMovements());
                    addMovement(returnMovements, MovementType.LEFT, movement.getNrOfMovements());
                    addMovement(returnMovements, MovementType.FORWARD, movement.getNrOfMovements());
                    turnedInReturnDirection = true;
                }
                if (isLastMovement(lastMovementsList, i)) {
                    addMovement(returnMovements, MovementType.LEFT, movement.getNrOfMovements());
                    addMovement(returnMovements, MovementType.LEFT, movement.getNrOfMovements());
                }
            } else if (MovementType.LEFT.equals(movement.getType())) {
                addMovement(returnMovements, MovementType.RIGHT, movement.getNrOfMovements());
            } else if (MovementType.RIGHT.equals(movement.getType())) {
                addMovement(returnMovements, MovementType.LEFT, movement.getNrOfMovements());
            }

        }
        return returnMovements;
    }

    private boolean isLastMovement(List<Movement> lastMovementsList, int i) {
        return i == lastMovementsList.size() - 1;
    }

    private void addMovement(List<Movement> returnMovements, MovementType type, Integer nrOfMovements) {
        final Movement moveForward = new Movement();
        moveForward.setType(type);
        moveForward.setNrOfMovements(nrOfMovements);
        returnMovements.add(moveForward);
    }

}
