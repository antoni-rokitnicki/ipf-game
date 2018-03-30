package com.ipf.automaticcarsgame.domain;

import com.ipf.automaticcarsgame.dto.car.CarType;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "CAR")
public class Car extends AudityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean crashed;

    @Enumerated(EnumType.STRING)
    private CarType type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        return name != null ? name.equals(car.name) : car.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", crashed=" + crashed +
                ", type=" + type +
                "} " + super.toString();
    }
}
