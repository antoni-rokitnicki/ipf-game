package com.ipf.automaticcarsgame.service.roadmap;

import java.util.Arrays;

public class CreateRoadmapRequest {
    private String name;
    private int[][] fields;

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
