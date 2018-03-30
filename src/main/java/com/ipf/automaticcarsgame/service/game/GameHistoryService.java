package com.ipf.automaticcarsgame.service.game;

import com.ipf.automaticcarsgame.domain.*;
import com.ipf.automaticcarsgame.repository.GameCarRepositoryCustom;
import com.ipf.automaticcarsgame.repository.MovementRepositoryCustom;
import com.ipf.automaticcarsgame.rest.HistoryRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            movementDto.setGame(carGame.getGame());
            movementDto.setMovements(movements);
            historyDto.getResult().add(movementDto);
        }
        return historyDto;
    }

    public static class HistoryDto {
        private List<MovementDto> result = new ArrayList<>();

        public static class MovementDto {
            private Game game;
            private Roadmap roadmap;
            private Car car;
            private List<Movement> movements;

            public Game getGame() {
                return game;
            }

            public void setGame(Game game) {
                this.game = game;
            }

            public Roadmap getRoadmap() {
                return roadmap;
            }

            public void setRoadmap(Roadmap roadmap) {
                this.roadmap = roadmap;
            }

            public Car getCar() {
                return car;
            }

            public void setCar(Car car) {
                this.car = car;
            }

            public List<Movement> getMovements() {
                return movements;
            }

            public void setMovements(List<Movement> movements) {
                this.movements = movements;
            }

            @Override
            public String toString() {
                return "MovementDto{" +
                        "game=" + game +
                        ", roadmap=" + roadmap +
                        ", car=" + car +
                        ", movements=" + movements +
                        '}';
            }
        }

        public List<MovementDto> getResult() {
            return result;
        }

        public void setResult(List<MovementDto> result) {
            this.result = result;
        }

        @Override
        public String toString() {
            return "HistoryDto{" +
                    "result=" + result +
                    '}';
        }
    }

}
