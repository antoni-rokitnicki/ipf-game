package com.ipf.automaticcarsgame.dto.game;

import com.ipf.automaticcarsgame.domain.Position;

public class GameCarRequest {
    private String roadmap;
    private String car;
    private Position position;

    public String getRoadmap() {
        return roadmap;
    }

    public void setRoadmap(String roadmap) {
        this.roadmap = roadmap;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameCarRequest)) return false;

        GameCarRequest that = (GameCarRequest) o;

        if (roadmap != null ? !roadmap.equals(that.roadmap) : that.roadmap != null) return false;
        if (car != null ? !car.equals(that.car) : that.car != null) return false;
        return position != null ? position.equals(that.position) : that.position == null;
    }

    @Override
    public int hashCode() {
        int result = roadmap != null ? roadmap.hashCode() : 0;
        result = 31 * result + (car != null ? car.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GameCarRequest{" +
                "roadmap='" + roadmap + '\'' +
                ", car='" + car + '\'' +
                ", position=" + position +
                '}';
    }
}
