package com.ipf.automaticcarsgame.service.collision;

import java.util.List;

public class CrashResult {
    private List<CrashedCarType> crashedCars;

    public CrashResult() {
    }

    public CrashResult(List<CrashedCarType> crashedCars) {
        this.crashedCars = crashedCars;
    }

    public List<CrashedCarType> getCrashedCars() {
        return crashedCars;
    }

    public void setCrashedCars(List<CrashedCarType> crashedCars) {
        this.crashedCars = crashedCars;
    }
}
