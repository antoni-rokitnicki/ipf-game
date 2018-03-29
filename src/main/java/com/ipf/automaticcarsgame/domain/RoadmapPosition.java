package com.ipf.automaticcarsgame.domain;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoadmapPosition that = (RoadmapPosition) o;

        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = position != null ? position.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RoadmapPosition{" +
                "position=" + position +
                ", value=" + value +
                '}';
    }
}
