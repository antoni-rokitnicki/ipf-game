package com.ipf.automaticcarsgame.service.game;

import com.ipf.automaticcarsgame.domain.GameCar;
import com.ipf.automaticcarsgame.domain.Movement;
import com.ipf.automaticcarsgame.dto.MovementType;
import com.ipf.automaticcarsgame.repository.MovementRepository;
import org.springframework.stereotype.Service;

@Service
public class MovementService {

    private final MovementRepository movementRepository;

    public MovementService(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }


    public void saveMovement(GameCar gameCar, MovementType movementType, Integer nrOfMovemetns) {
        final Movement movement = new Movement();
        movement.setType(movementType);
        movement.setGameCar(gameCar);
        movement.setGameCarId(gameCar.getId());
        movement.setNrOfMovements(nrOfMovemetns);
        movementRepository.save(movement);
    }


}
