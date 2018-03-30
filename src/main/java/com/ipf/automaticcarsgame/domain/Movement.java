package com.ipf.automaticcarsgame.domain;

import com.ipf.automaticcarsgame.dto.MovementType;

import javax.persistence.*;

@Entity
@Table(name = "MOVEMENT")
public class Movement extends AudityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "GAME_CAR_ID")
    private Integer gameCarId;

    @ManyToOne
    @JoinColumn(name = "GAME_CAR_ID", insertable = false, updatable = false)
    private GameCar gameCar;

    @Enumerated(EnumType.STRING)
    private MovementType type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGameCarId() {
        return gameCarId;
    }

    public void setGameCarId(Integer gameCarId) {
        this.gameCarId = gameCarId;
    }

    public GameCar getGameCar() {
        return gameCar;
    }

    public void setGameCar(GameCar gameCar) {
        this.gameCar = gameCar;
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movement)) return false;

        Movement movement = (Movement) o;

        return id != null ? id.equals(movement.id) : movement.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Movement{" +
                "id=" + id +
                ", gameCarId=" + gameCarId +
                ", gameCar=" + gameCar +
                ", type=" + type +
                "} " + super.toString();
    }
}
