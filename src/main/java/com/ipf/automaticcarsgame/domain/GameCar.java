package com.ipf.automaticcarsgame.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "GAME_CAR")
public class GameCar extends AudityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "GAME_ID")
    private Integer gameId;

    @ManyToOne
    @JoinColumn(name = "GAME_ID", insertable = false, updatable = false)
    private Game game;

    @Column(name = "CAR_ID")
    private Integer carId;

    @ManyToOne
    @JoinColumn(name = "CAR_ID", insertable = false, updatable = false)
    private Car car;

    @Column(name = "POSITION_ID")
    private Integer positionId;

    @Column(name = "CURRNET_DIRECTION")
    @Enumerated(EnumType.STRING)
    private DirectionType currnetDirection = DirectionType.NORTH;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public DirectionType getCurrnetDirection() {
        return currnetDirection;
    }

    public void setCurrnetDirection(DirectionType currnetDirection) {
        this.currnetDirection = currnetDirection;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameCar)) return false;

        GameCar gameCar = (GameCar) o;

        return id != null ? id.equals(gameCar.id) : gameCar.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "GameCar{" +
                "id=" + id +
                ", gameId=" + gameId +
                ", game=" + game +
                ", carId=" + carId +
                ", car=" + car +
                ", positionId=" + positionId +
                ", currnetDirection=" + currnetDirection +
                ", deleted=" + deleted +
                '}';
    }

}
