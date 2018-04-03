package com.ipf.automaticcarsgame.dto.car;

import java.time.ZonedDateTime;

public class CarDto {
    private String carName;
    private CarType type;
    private boolean crashed;
    private ZonedDateTime insertDate;
    private ZonedDateTime updateDate;

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public boolean isCrashed() {
        return crashed;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    public ZonedDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(ZonedDateTime insertDate) {
        this.insertDate = insertDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarDto)) return false;

        CarDto that = (CarDto) o;

        if (crashed != that.crashed) return false;
        if (carName != null ? !carName.equals(that.carName) : that.carName != null) return false;
        if (type != that.type) return false;
        if (insertDate != null ? !insertDate.equals(that.insertDate) : that.insertDate != null) return false;
        return updateDate != null ? updateDate.equals(that.updateDate) : that.updateDate == null;
    }

    @Override
    public int hashCode() {
        int result = carName != null ? carName.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (crashed ? 1 : 0);
        result = 31 * result + (insertDate != null ? insertDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CarDto{" +
                "carName='" + carName + '\'' +
                ", type=" + type +
                ", crashed=" + crashed +
                ", insertDate=" + insertDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
