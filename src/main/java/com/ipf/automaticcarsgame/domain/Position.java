package com.ipf.automaticcarsgame.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

// todo czy jest dobra nazwa?
@Embeddable
public class Position {

    public Position() {
    }

    public Position(Integer row, Integer col) {
        this.row = row;
        this.col = col;
    }

    private Integer row;
    private Integer col;

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (row != null ? !row.equals(position.row) : position.row != null) return false;
        return col != null ? col.equals(position.col) : position.col == null;
    }

    @Override
    public int hashCode() {
        int result = row != null ? row.hashCode() : 0;
        result = 31 * result + (col != null ? col.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
