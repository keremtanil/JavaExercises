package com.fleet.main;

import com.fleet.interfaces.Maintainable;
import com.fleet.models.base.Vehicle;
import com.fleet.models.vehicles.Car;
import com.fleet.models.vehicles.ElectricCar;
import com.fleet.models.vehicles.Truck;
import com.fleet.services.AuditService;
import com.fleet.services.FleetManager;

public class main {
    public static void main(String[] args) {
        AuditService.logTransaction("Akıllı Filo Yönetim Sistemi Başlatılıyor...");

        Vehicle myCar = new Car("CAR-101", "Renault", "Megane", 1000.0, 4, true);
        Vehicle myTruck = new Truck("TRK-999", "Mercedes-Benz", "Actros", 3000.0, 10, 15.0, 12000);
        Vehicle myTesla = new ElectricCar("ELC-404", "Tesla", "Model Y", 2500.0, 4, 85);

        Vehicle[] fleet = {myCar, myTruck, myTesla};

        System.out.println("\n--- FİLO GENEL BİLGİLERİ ---");
        System.out.println("Filodaki Toplam Araç Sayısı : " + Vehicle.totalVehiclesInFleet);
        System.out.println("Garaj Merkez Konumu         : " + FleetManager.GPSLocator.getGarageCoordinates());
        System.out.println("--------------------------------------------------\n");

        for (Vehicle vehicle : fleet) {
            vehicle.startEngine();
            vehicle.showFuelEfficiency();

            if (vehicle instanceof Maintainable) {
                Maintainable maintainableVehicle = (Maintainable) vehicle;

                if (maintainableVehicle.isNeedsMaintenance()) {
                    AuditService.logTransaction(vehicle.getVehicleId() + " için kilometre sınırı aşılmış, bakım gerekiyor!");
                    maintainableVehicle.performMaintenance();
                }
            }
            System.out.println("--------------------------------------------------");
            }

            FleetManager manager = new FleetManager();

            manager.printRentalContract(myCar, 3);
            manager.printRentalContract(myTesla, 5, 10.0);

            System.out.println("Zaten kirada olan aracı tekrar kiralamayı deniyoruz:");
            myCar.rentOut();

            AuditService.logSecurityAlert(myCar.getVehicleId());

            myCar.returnVehicle();

            AuditService.logTransaction("Sistem Günlükleri Kapatıldı. İyi Günler!");
        }
}
