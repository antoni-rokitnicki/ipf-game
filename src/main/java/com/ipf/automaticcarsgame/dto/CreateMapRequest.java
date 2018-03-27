package com.ipf.automaticcarsgame.dto;

import java.util.Arrays;

public class CreateMapRequest {
    private String name;
    private String[] map;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getMap() {
        return map;
    }

    public void setMap(String[] map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "CreateMapRequest{" +
                "name='" + name + '\'' +
                ", map=" + Arrays.toString(map) +
                '}';
    }
}
