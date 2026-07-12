package com.fleet.models.vehicles;

import com.fleet.models.base.LandVehicle;

public final class ElectricCar extends LandVehicle {
    private int batteryCapacityKwH;

    public int getBatteryCapacityKwH() {
        return batteryCapacityKwH;
    }

    public void setBatteryCapacityKwH(int batteryCapacityKwH) {
        this.batteryCapacityKwH = batteryCapacityKwH;
    }

    public ElectricCar(String vehicleId, String brand, String model, double baseDailyRate, int wheelCount, int batteryCapacityKwH) {
        super(vehicleId, brand, model, baseDailyRate, wheelCount);
        this.batteryCapacityKwH = batteryCapacityKwH;
    }

    @Override
    public void showFuelEfficiency() {
        System.out.println(getBrand() + " " + getModel() + " ortalama 16.5 kWh/100km elektrik tüketir.");
    }

    @Override
    public double calculateRentalCost(int days) {
        double normalTotal = getBaseDailyRate() * days;

        double discountedTotal = normalTotal - (normalTotal * 0.15);

        if (batteryCapacityKwH > 80) {
            discountedTotal += (200 * days);
        }

        return discountedTotal;
    }
}
