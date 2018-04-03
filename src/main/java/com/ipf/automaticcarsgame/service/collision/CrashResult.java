package com.ipf.automaticcarsgame.service.collision;

import java.util.List;

class CrashResult {
    private List<CrashedCarType> crashedCar;

    public CrashResult() {
    }

    public CrashResult(List<CrashedCarType> crashedCar) {
        this.crashedCar = crashedCar;
    }

    public List<CrashedCarType> getCrashedCar() {
        return crashedCar;
    }

    public void setCrashedCar(List<CrashedCarType> crashedCar) {
        this.crashedCar = crashedCar;
    }
}
