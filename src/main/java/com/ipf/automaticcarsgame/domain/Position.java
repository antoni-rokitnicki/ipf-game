package com.ipf.automaticcarsgame.domain;

import javax.persistence.*;

@Entity
@Table(name = "POSITION")
public class Position extends AudityEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "MAP_ID")
    private Integer mapId;

    @ManyToOne
    @JoinColumn(name = "map_id", insertable = false, updatable = false)
    private Map map;

    @Column(name = "ROW_IDX")
    private Integer rowIdx;

    @Column(name = "COL_IDX")
    private Integer colIdx;

    private Integer value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Integer getRowIdx() {
        return rowIdx;
    }

    public void setRowIdx(Integer rowIdx) {
        this.rowIdx = rowIdx;
    }

    public Integer getColIdx() {
        return colIdx;
    }

    public void setColIdx(Integer colIdx) {
        this.colIdx = colIdx;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;

        Position position = (Position) o;

        return id != null ? id.equals(position.id) : position.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", mapId=" + mapId +
                ", map=" + map +
                ", rowIdx=" + rowIdx +
                ", colIdx=" + colIdx +
                ", value=" + value +
                "} " + super.toString();
    }
}
