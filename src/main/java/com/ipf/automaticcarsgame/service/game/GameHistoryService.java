package com.ipf.automaticcarsgame.service.game;

import com.ipf.automaticcarsgame.domain.GameCar;
import com.ipf.automaticcarsgame.domain.Movement;
import com.ipf.automaticcarsgame.dto.HistoryDto;
import com.ipf.automaticcarsgame.dto.HistoryRequest;
import com.ipf.automaticcarsgame.repository.GameCarRepositoryCustom;
import com.ipf.automaticcarsgame.repository.MovementRepositoryCustom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameHistoryService {
    private final MovementRepositoryCustom movementRepositoryCustom;
    private final GameCarRepositoryCustom gameCarRepositoryCustom;

    public GameHistoryService(MovementRepositoryCustom movementRepositoryCustom, GameCarRepositoryCustom gameCarRepositoryCustom) {
        this.movementRepositoryCustom = movementRepositoryCustom;
        this.gameCarRepositoryCustom = gameCarRepositoryCustom;
    }


    public HistoryDto getHistory(HistoryRequest historyRequest) {
        // todo  dokoncz
        final List<GameCar> games = gameCarRepositoryCustom.findGamesCar(historyRequest.getGameId(), historyRequest.getMapName(), historyRequest.getCarName());
        final HistoryDto historyDto = new HistoryDto();
        for (GameCar carGame : games) {
            final List<Movement> movements = movementRepositoryCustom.findMovements(carGame, historyRequest.getLimit());
            final HistoryDto.MovementDto movementDto = new HistoryDto.MovementDto();
            movementDto.setCar(carGame.getCar());
            movementDto.setGame(mapToGameDto(carGame));
            movementDto.setMovements(movements);
            historyDto.getResult().add(movementDto);
        }
        return historyDto;
    }

    private HistoryDto.MovementDto.GameDto mapToGameDto(GameCar carGame) {
        final HistoryDto.MovementDto.GameDto gameDto = new HistoryDto.MovementDto.GameDto();
        gameDto.setId(carGame.getGame().getId());
        gameDto.setFinishDate(carGame.getGame().getFinishDate());
        return gameDto;
    }

}
