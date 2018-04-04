package com.ipf.automaticcarsgame.dto.car;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class CarRequest {
    @ApiModelProperty(value = "Car's name")
    private String name;

    @ApiModelProperty(value = "Car types", allowableValues = "NORMAL_CAR, MONSTER_TRAC, RACER")
    private String type;

    public CarRequest() {
    }

    public CarRequest(String name) {
        this.name = name;
    }

    public CarRequest(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarRequest that = (CarRequest) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CarRequest{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
