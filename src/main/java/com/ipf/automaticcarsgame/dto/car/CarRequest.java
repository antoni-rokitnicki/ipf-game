package com.ipf.automaticcarsgame.dto.car;

public class CarRequest {
    private String name;
    private String type;
    private Boolean crashed;

    public CarRequest() {
    }

    public Boolean getCrashed() {
        return crashed;
    }

    public void setCrashed(Boolean crashed) {
        this.crashed = crashed;
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
                ", crashed='" + crashed + '\'' +
                '}';
    }
}
