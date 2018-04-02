package com.ipf.automaticcarsgame.dto;

import com.ipf.automaticcarsgame.domain.Car;
import com.ipf.automaticcarsgame.domain.Movement;
import com.ipf.automaticcarsgame.domain.Roadmap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryDto {
    private List<MovementDto> result = new ArrayList<>();

    public static class MovementDto {
        private GameDto game;
        private Roadmap roadmap;
        private Car car;
        private List<Movement> movements;

        public GameDto getGame() {
            return game;
        }

        public void setGame(GameDto game) {
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


        public static class GameDto {
            private Integer id;
            private Date finishDate;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Date getFinishDate() {
                return finishDate;
            }

            public void setFinishDate(Date finishDate) {
                this.finishDate = finishDate;
            }

            @Override
            public String toString() {
                return "GameDto{" +
                        "id=" + id +
                        ", finishDate=" + finishDate +
                        '}';
            }
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
