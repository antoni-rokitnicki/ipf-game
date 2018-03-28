package com.ipf.automaticcarsgame.domain;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

// todo czy jest dobra nazwa?
@Embeddable
public class RoadmapPosition {

    public RoadmapPosition() {
    }

    public RoadmapPosition(Position position, Byte value) {
        this.position = position;
        this.value = value;
    }

    @Embedded
    private Position position;

    private Byte value;

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


}
