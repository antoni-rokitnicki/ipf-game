package com.ipf.automaticcarsgame.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "GAME")
public class Game extends AudityEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "MAP_ID")
    private Integer mapId;

    @ManyToOne
    @JoinColumn(name = "map_id", insertable = false, updatable = false)
    private Map map;

    @Column(name = "FINISH_DATE")
    private Date finishDate;

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

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;

        Game game = (Game) o;

        return id != null ? id.equals(game.id) : game.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", mapId=" + mapId +
                ", map=" + map +
                ", finishDate=" + finishDate +
                "} " + super.toString();
    }
}