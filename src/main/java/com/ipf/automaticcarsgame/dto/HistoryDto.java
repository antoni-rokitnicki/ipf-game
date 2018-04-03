package com.ipf.automaticcarsgame.dto;

import com.ipf.automaticcarsgame.domain.Movement;
import com.ipf.automaticcarsgame.domain.Roadmap;
import com.ipf.automaticcarsgame.dto.car.CarDto;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryDto {
    private List<HistoryDetailsDto> result = new ArrayList<>();

    public static class HistoryDetailsDto {
        private GameDto game;
        private Roadmap roadmap;
        private CarDto car;
        private List<MovementDto> movements;

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

        public CarDto getCar() {
            return car;
        }

        public void setCar(CarDto car) {
            this.car = car;
        }

        public List<MovementDto> getMovements() {
            return movements;
        }

        public void setMovements(List<MovementDto> movements) {
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

        public static class MovementDto {
            private Integer id;
            private String type;
            private ZonedDateTime data;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public ZonedDateTime getData() {
                return data;
            }

            public void setData(ZonedDateTime data) {
                this.data = data;
            }

            @Override
            public String toString() {
                return "MovementDto{" +
                        "id=" + id +
                        ", type='" + type + '\'' +
                        ", data=" + data +
                        '}';
            }
        }
    }


    public List<HistoryDetailsDto> getResult() {
        return result;
    }

    public void setResult(List<HistoryDetailsDto> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "HistoryDto{" +
                "result=" + result +
                '}';
    }
}
