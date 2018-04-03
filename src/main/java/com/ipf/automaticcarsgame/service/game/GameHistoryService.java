package com.ipf.automaticcarsgame.service.game;

import com.ipf.automaticcarsgame.domain.GameCar;
import com.ipf.automaticcarsgame.domain.Movement;
import com.ipf.automaticcarsgame.dto.HistoryDto;
import com.ipf.automaticcarsgame.dto.HistoryRequest;
import com.ipf.automaticcarsgame.mapper.CarMapper;
import com.ipf.automaticcarsgame.repository.GameCarRepositoryCustom;
import com.ipf.automaticcarsgame.repository.MovementRepositoryCustom;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class GameHistoryService {
    private final MovementRepositoryCustom movementRepositoryCustom;
    private final GameCarRepositoryCustom gameCarRepositoryCustom;

    public GameHistoryService(MovementRepositoryCustom movementRepositoryCustom, GameCarRepositoryCustom gameCarRepositoryCustom) {
        this.movementRepositoryCustom = movementRepositoryCustom;
        this.gameCarRepositoryCustom = gameCarRepositoryCustom;
    }


    public HistoryDto getHistory(HistoryRequest historyRequest) {
        final List<GameCar> games = gameCarRepositoryCustom.findGamesCar(historyRequest.getGameId(), historyRequest.getMapName(), historyRequest.getCarName());
        final HistoryDto historyDto = new HistoryDto();
        for (GameCar carGame : games) {
            final List<Movement> movements = movementRepositoryCustom.findMovements(carGame, historyRequest.getLimit());
            final HistoryDto.HistoryDetailsDto movementDerailsDto = new HistoryDto.HistoryDetailsDto();
            movementDerailsDto.setCar(CarMapper.map(carGame.getCar()));
            movementDerailsDto.setGame(mapToGameDto(carGame));
            movementDerailsDto.setMovements(mapToMovementDtoList(movements));
            historyDto.getResult().add(movementDerailsDto);
        }
        return historyDto;
    }

    private List<HistoryDto.HistoryDetailsDto.MovementDto> mapToMovementDtoList(List<Movement> movements) {
        return movements.stream().map(mov -> {
            final HistoryDto.HistoryDetailsDto.MovementDto movementDto = new HistoryDto.HistoryDetailsDto.MovementDto();
            movementDto.setId(mov.getId());
            movementDto.setType(mov.getType().name());
            movementDto.setData(mov.getInsertDate());
            return movementDto;
        }).collect(toList());
    }

    private HistoryDto.HistoryDetailsDto.GameDto mapToGameDto(GameCar carGame) {
        final HistoryDto.HistoryDetailsDto.GameDto gameDto = new HistoryDto.HistoryDetailsDto.GameDto();
        gameDto.setId(carGame.getGame().getId());
        gameDto.setFinishDate(carGame.getGame().getFinishDate());
        return gameDto;
    }

}
