package com.fleet.services;

import com.fleet.models.base.Vehicle;

public class FleetManager {
    public void printRentalContract(Vehicle vehicle, int days) {
        System.out.println("\n==============================================");
        System.out.println("            STANDART KİRALAMA SÖZLEŞMESİ      ");
        System.out.println("==============================================");
        System.out.println("Araç Bilgisi : " + vehicle.getBrand() + " " + vehicle.getModel());
        System.out.println("Araç ID      : " + vehicle.getVehicleId());
        System.out.println("Kiralama     : " + days + " Gün");

        double cost = vehicle.calculateRentalCost(days);
        System.out.println("TOPLAM TUTAR : " + cost + " TL");
        System.out.println("----------------------------------------------");

        vehicle.rentOut();
        System.out.println("==============================================\n");
    }

    public void printRentalContract(Vehicle vehicle, int days, double discountRate) {
        System.out.println("\n==============================================");
        System.out.println("      İNDİRİMLİ KURUMSAL KİRALAMA SÖZLEŞMESİ  ");
        System.out.println("==============================================");
        System.out.println("Araç Bilgisi : " + vehicle.getBrand() + " " + vehicle.getModel());
        System.out.println("Araç ID      : " + vehicle.getVehicleId());
        System.out.println("Kiralama     : " + days + " Gün");
        System.out.println("İndirim Oranı: %" + discountRate);

        double normalCost = vehicle.calculateRentalCost(days);
        double discountedCost = normalCost - (normalCost * (discountRate / 100));

        System.out.println("Normal Tutar : " + normalCost + " TL");
        System.out.println("İNDİRİMLİ    : " + discountedCost + " TL");
        System.out.println("----------------------------------------------");

        vehicle.rentOut();
        System.out.println("==============================================\n");

    }
    public static class GPSLocator {
        public static String getGarageCoordinates() {
            return "41.0082° N, 28.9784° E (İstanbul Merkez Filo Garajı)";
        }
    }
}