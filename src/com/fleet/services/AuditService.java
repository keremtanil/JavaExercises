package com.fleet.services;

public final class AuditService {
    private AuditService() {

    }

    public static void logTransaction(String message) {
        System.out.println("[SİSTEM LOG] : " + message);
    }

    public static void logSecurityAlert(String vehicleId) {
        System.out.println("[GÜVENLİK UYARISI] : " + vehicleId + " ID'li araç için güvenlik protokolü ve motor kilidi tetiklendi!");
    }
}
