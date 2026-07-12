package com.fleet.models.base;
import com.fleet.interfaces.Rentable;

public abstract class Vehicle implements Rentable {
    public String getVehicleId() {
        return vehicleId;
    }

    protected String vehicleId;
    private String brand;
    private String model;
    private double baseDailyRate;
    private boolean isAvailable = true;
    public static int totalVehiclesInFleet = 0;

    public Vehicle(String vehicleId,String brand,String model,double baseDailyRate){
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.baseDailyRate = baseDailyRate;

        totalVehiclesInFleet++;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getBaseDailyRate() {
        return baseDailyRate;
    }

    public void setBaseDailyRate(double baseDailyRate) {
        this.baseDailyRate = baseDailyRate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public final void startEngine(){
        System.out.println("Motor güvenlik kontrolleri yapıldı. " + getBrand() + " " + getModel() + " çalıştırıldı.");
    }

    public abstract void showFuelEfficiency();

    @Override
    public void rentOut(){
        if(isAvailable){
            isAvailable = false;
            System.out.println(getBrand() + " " + getModel() + " başarıyla kiralandı.");
        }
        else{
            System.out.println("HATA: " + brand + " " + model + " zaten kirada!");
        }
    }

    @Override
    public void returnVehicle(){
        isAvailable = true;
        System.out.println(getBrand() + " " + getModel() + " başarıyla teslim alındı.");
    }

}
