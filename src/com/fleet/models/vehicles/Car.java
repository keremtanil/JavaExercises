package com.fleet.models.vehicles;

import com.fleet.models.base.LandVehicle;

public class Car extends LandVehicle {
    private boolean hasSunroof;

    public boolean isHasSunroof() {
        return hasSunroof;
    }

    public void setHasSunroof(boolean hasSunroof) {
        this.hasSunroof = hasSunroof;
    }

    public Car(String vehicleId, String brand, String model, double baseDailyRate, int wheelCount, boolean hasSunroof) {
        super(vehicleId, brand, model, baseDailyRate, wheelCount);
        this.hasSunroof = hasSunroof;
    }

    @Override
    public void showFuelEfficiency() {
        System.out.println(getBrand() + " " + getModel() + " ortalama 6.5L/100km yakıt tüketir.");
    }

    @Override
    public double calculateRentalCost(int days) {
        double total = getBaseDailyRate() * days;

        if (hasSunroof){
            total += (50 * days);
        }
        return total;
    }
}
