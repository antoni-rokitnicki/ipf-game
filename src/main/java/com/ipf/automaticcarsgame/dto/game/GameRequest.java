package com.ipf.automaticcarsgame.dto.game;

public class GameRequest {
    private String roadMapName;

    public String getRoadMapName() {
        return roadMapName;
    }

    public void setRoadMapName(String roadMapName) {
        this.roadMapName = roadMapName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameRequest)) return false;

        GameRequest that = (GameRequest) o;

        return roadMapName != null ? roadMapName.equals(that.roadMapName) : that.roadMapName == null;
    }

    @Override
    public int hashCode() {
        return roadMapName != null ? roadMapName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "GameRequest{" +
                "roadMapName='" + roadMapName + '\'' +
                '}';
    }
}
