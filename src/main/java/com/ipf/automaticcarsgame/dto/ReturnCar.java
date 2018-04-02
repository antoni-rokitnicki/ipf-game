package com.ipf.automaticcarsgame.dto;

public class ReturnCar {
    private int noOfMovements;

    public int getNoOfMovements() {
        return noOfMovements;
    }

    public void setNoOfMovements(int noOfMovements) {
        this.noOfMovements = noOfMovements;
    }

    @Override
    public String toString() {
        return "ReturnCar{" +
                "noOfMovements=" + noOfMovements +
                '}';
    }
}
