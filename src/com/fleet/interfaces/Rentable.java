package com.fleet.interfaces;

public interface Rentable {
    double calculateRentalCost(int days);
    void rentOut();
    void returnVehicle();
}
