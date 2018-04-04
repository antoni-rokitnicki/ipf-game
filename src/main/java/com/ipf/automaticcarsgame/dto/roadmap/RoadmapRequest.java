package com.ipf.automaticcarsgame.dto.roadmap;

import java.util.Arrays;

public class RoadmapRequest {
    private String name;
    private int[][] fields; //todo change to byte[]

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

    @Override
    public String toString() {
        return "CreateRoadmapRequest{" +
                "name='" + name + '\'' +
                ", fields=" + Arrays.toString(fields) +
                '}';
    }
}
