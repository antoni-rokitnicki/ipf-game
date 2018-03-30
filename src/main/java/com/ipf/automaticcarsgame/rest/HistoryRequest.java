package com.ipf.automaticcarsgame.rest;

import java.util.List;

public class HistoryRequest {
    private List<Integer> gameId;
    private List<String> mapName;
    private List<String> carName;
    private int limit;

    public List<Integer> getGameId() {
        return gameId;
    }

    public void setGameId(List<Integer> gameId) {
        this.gameId = gameId;
    }

    public List<String> getMapName() {
        return mapName;
    }

    public void setMapName(List<String> mapName) {
        this.mapName = mapName;
    }

    public List<String> getCarName() {
        return carName;
    }

    public void setCarName(List<String> carName) {
        this.carName = carName;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "HistoryRequest{" +
                "gameId=" + gameId +
                ", mapName=" + mapName +
                ", carName=" + carName +
                ", limit=" + limit +
                '}';
    }
}
