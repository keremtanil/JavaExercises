package com.fleet.models.base;

public abstract class LandVehicle extends Vehicle{
    private int wheelCount;

    public LandVehicle(String vehicleId, String brand, String model, double baseDailyRate, int wheelCount) {
        super(vehicleId, brand, model, baseDailyRate);
        this.wheelCount = wheelCount;
    }

    public int getWheelCount() {
        return wheelCount;
    }

    public void setWheelCount(int wheelCount) {
        this.wheelCount = wheelCount;
    }
}
