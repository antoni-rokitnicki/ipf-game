package com.ipf.automaticcarsgame.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ROADMAP")
public class Roadmap extends AudityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean deleted;

    // todo powinnabyc //private Map<Position, RoadmapPosition> positions;
    @ElementCollection
    @CollectionTable(name = "ROADMAP_POSITION", joinColumns = @JoinColumn(name = "MAP_ID"))
    private List<RoadmapPosition> positions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<RoadmapPosition> getPositions() {
        return positions;
    }

    public void setPositions(List<RoadmapPosition> positions) {
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Roadmap)) return false;

        Roadmap map = (Roadmap) o;

        return name != null ? name.equals(map.name) : map.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Map{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deleted=" + deleted +
                "} " + super.toString();
    }
}
