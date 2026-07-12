package com.fleet.models.vehicles;

import com.fleet.interfaces.Maintainable;
import com.fleet.models.base.LandVehicle;

public class Truck extends LandVehicle implements Maintainable {
    private double loadCapacityInTons;
    private int totalKm;

    public Truck(String vehicleId, String brand, String model, double baseDailyRate, int wheelCount, double loadCapacityInTons, int totalKm) {
        super(vehicleId, brand, model, baseDailyRate, wheelCount);
        this.loadCapacityInTons = loadCapacityInTons;
        this.totalKm = totalKm;
    }

    public double getLoadCapacityInTons() {
        return loadCapacityInTons;
    }

    public void setLoadCapacityInTons(double loadCapacityInTons) {
        this.loadCapacityInTons = loadCapacityInTons;
    }

    public int getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(int totalKm) {
        this.totalKm = totalKm;
    }

    @Override
    public boolean isNeedsMaintenance() {
        return totalKm > 10000;
    }

    @Override
    public void performMaintenance() {
        System.out.println("Kamyon bakıma alındı, yağ ve frenler değişti.");
        totalKm = 0;
    }

    @Override
    public void showFuelEfficiency() {
        System.out.println(getBrand() + " " + getModel() + " ortalama 25L/100km yakıt tüketir.");
    }

    @Override
    public double calculateRentalCost(int days) {
        double total = (getBaseDailyRate() * days) + (loadCapacityInTons * 150 * days);
        return total;
    }
}
