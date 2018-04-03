package com.ipf.automaticcarsgame.dto.game;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RemoveGameCarRequest {
    private String roadmap;
    private String car;

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

    @JsonIgnore
    public GameCarRequest getGameCarRequest(){
        return new GameCarRequest(this.roadmap, this.car);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RemoveGameCarRequest that = (RemoveGameCarRequest) o;

        if (roadmap != null ? !roadmap.equals(that.roadmap) : that.roadmap != null) return false;
        return car != null ? car.equals(that.car) : that.car == null;
    }

    @Override
    public int hashCode() {
        int result = roadmap != null ? roadmap.hashCode() : 0;
        result = 31 * result + (car != null ? car.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GameCarRequest{" +
                "roadmap='" + roadmap + '\'' +
                ", car='" + car + '\'' +
                '}';
    }
}
