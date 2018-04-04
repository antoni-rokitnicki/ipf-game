package com.ipf.automaticcarsgame.dto.roadmap;

import com.ipf.automaticcarsgame.domain.Position;

public class RoadmapPositionDto {
    private Position position;
    private Byte value;

    public RoadmapPositionDto() {
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "RoadmapPositionDto{" +
                "position=" + position +
                ", value=" + value +
                '}';
    }
}
