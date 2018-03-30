package com.ipf.automaticcarsgame.domain;

import javax.persistence.*;

@Entity
@Table(name = "ROADMAP_POSITION")
public class RoadmapPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private Position position;

    private Byte value;

    @ManyToOne
    @JoinColumn(name = "MAP_ID", insertable = false, updatable = false)
    private Roadmap roadmap;

    public RoadmapPosition() {
    }

    public RoadmapPosition(Position position, Byte value) {
        this.position = position;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Roadmap getRoadmap() {
        return roadmap;
    }

    public void setRoadmap(Roadmap roadmap) {
        this.roadmap = roadmap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoadmapPosition)) return false;

        RoadmapPosition that = (RoadmapPosition) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RoadmapPosition{" +
                "id=" + id +
                ", position=" + position +
                ", value=" + value +
                '}';
    }
}
