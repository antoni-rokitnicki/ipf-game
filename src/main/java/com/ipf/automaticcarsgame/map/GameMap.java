package com.ipf.automaticcarsgame.map;

public class GameMap {
    private String name;
    private int[][] fields;
    private String status;
    private boolean deleted;


    public boolean isRoad(int row, int col) {
        if (row >= fields.length || col >= fields.length) {
            return false;
        }
        return fields[row][col] == 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[][] getFields() {
        return fields;
    }

    public void setFields(int[][] fields) {
        this.fields = fields;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
