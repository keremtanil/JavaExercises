# Smart Fleet Management System (Akıllı Filo ve Güvenlik Yönetim Sistemi)

Bu proje, Java'da **Nesne Yönelimli Programlama (OOP)** ilkelerinin temel ve ileri seviye kavramlarını gerçek hayat senaryosuna dayalı kurumsal bir mimari üzerinde pratik etmek amacıyla geliştirilmiştir. 

Proje; farklı araç tiplerinin (Otomobil, Kamyon, Elektrikli Araç) kiralanması, bakım sürelerinin takibi, loglama ve güvenlik denetimlerinin tek bir merkezden yönetilmesini simüle eder.

---

## Proje Dosya ve Paket Dizeri (Project Structure)

Projemiz, sorumlulukların net bir şekilde ayrıldığı kurumsal bir paket mimarisi ile inşa edilmiştir:

```text
src/
└── com/
    └── fleet/
        ├── interfaces/
        │   ├── Maintainable.java
        │   └── Rentable.java
        ├── models/
        │   ├── base/
        │   │   ├── LandVehicle.java
        │   │   └── Vehicle.java
        │   └── vehicles/
        │       ├── Car.java
        │       ├── ElectricCar.java
        │       └── Truck.java
        ├── services/
        │   ├── AuditService.java
        │   └── FleetManager.java
        └── main/
            └── main.java
```

---

## Projede Kullanılan OOP Kavramları ve Kod Eşleştirmeleri

Aşağıda, proje mimarimizde kullanılan temel OOP kavramları, bu kavramların kısa tanımları ve projemizdeki gerçek kod karşılıkları detaylandırılmıştır.

### 1. Encapsulation (Kapsülleme) & Access Modifiers
> **Teorik Tanım:** Sınıf içindeki verileri (değişkenleri) dış dünyadan gizlemek ve bu verilere sadece izin verilen kontrollü metotlar (Getter/Setter) aracılığıyla erişilmesini sağlamaktır.
* **Projemizdeki Gerçek Kod:**
  `Vehicle.java` sınıfımızda `brand`, `model` ve `baseDailyRate` gibi değişkenlerimizi `private` ve `protected` tanımladık. Bir aracın kiralama durumu (`isAvailable`) doğrudan dışarıdan değiştirilemez; sadece `rentOut()` metodu çalıştığında kontrollü bir şekilde `false` yapılır. Zaten kirada olan bir araç tekrar kiralanmak istendiğinde sistem bu durumu engeller:

```java
// Vehicle.java içindeki gerçek kapsülleme kodumuz:
public abstract class Vehicle implements Rentable {
    protected String vehicleId;
    private String brand;
    private String model;
    private double baseDailyRate;
    private boolean isAvailable = true; // Dışarıdan direkt müdahale edilemez!

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
}
```

---

### 2. Inheritance (Kalıtım / Miras Alma)
> **Teorik Tanım:** Bir alt sınıfın (Subclass), üst sınıfın (Superclass) özelliklerini ve davranışlarını devralmasıdır. Kod tekrarını önler ve hiyerarşi kurar.
* **Projemizdeki Gerçek Kod:**
  Projemizde 2 katmanlı bir kalıtım hiyerarşisi kurduk. Önce tüm kara araçları için `Vehicle` sınıfından türeyen `LandVehicle` soyut sınıfını oluşturduk (`wheelCount` ekledik). Sonrasında `Car`, `Truck` ve `ElectricCar` sınıflarımızı `LandVehicle` sınıfından türettik. Alt sınıflarımız `super(...)` ile üst sınıfların constructor'larını besler:

```java
// LandVehicle.java (1. Katman Kalıtım)
public abstract class LandVehicle extends Vehicle {
    private int wheelCount;
    public LandVehicle(String vehicleId, String brand, String model, double baseDailyRate, int wheelCount) {
        super(vehicleId, brand, model, baseDailyRate);
        this.wheelCount = wheelCount;
    }
}

// Car.java (2. Katman Kalıtım - Gerçek Kodumuz)
public class Car extends LandVehicle {
    private boolean hasSunroof;

    public Car(String vehicleId, String brand, String model, double baseDailyRate, int wheelCount, boolean hasSunroof) {
        super(vehicleId, brand, model, baseDailyRate, wheelCount);
        this.hasSunroof = hasSunroof;
    }
}
```

---

### 3. Polymorphism (Çok Biçimlilik) & Reference Types
> **Teorik Tanım:** Ata sınıf referansının, kendisinden türeyen farklı alt sınıf nesnelerini tutabilmesi ve aynı metodun farklı nesnelerde farklı şekilde davranabilmesidir.
* **Projemizdeki Gerçek Kod:**
  `main.java` sınıfımızda referans tipimizi Ata Sınıf (`Vehicle`) olarak belirlememize rağmen, alt sınıflarımızdan (`Car`, `Truck`, `ElectricCar`) nesneler ürettik. Bu nesneleri tek bir `Vehicle[] fleet` dizisi içinde toplayıp döngüye aldık. Döngü içinde her araca `showFuelEfficiency()` dediğimizde, her biri kendi yakıt tipine göre farklı çıktı üretti:

```java
// main.java içindeki gerçek polimorfik filo dizimiz:
Vehicle myCar = new Car("CAR-101", "Renault", "Megane", 1000.0, 4, true);
Vehicle myTruck = new Truck("TRK-999", "Mercedes-Benz", "Actros", 3000.0, 10, 15.0, 12000);
Vehicle myTesla = new ElectricCar("ELC-404", "Tesla", "Model Y", 2500.0, 4, 85);

// Farklı alt sınıflar, tek bir Ata Sınıf (Vehicle) referansında toplandı!
Vehicle[] fleet = {myCar, myTruck, myTesla};

for (Vehicle vehicle : fleet) {
    vehicle.startEngine();
    vehicle.showFuelEfficiency(); // Her araç kendi ezdiği (overrode) metodu çalıştırır!
}
```

---

### 4. Abstraction (Soyutlama) & Abstract Methods
> **Teorik Tanım:** Bir nesnenin *ne* yapacağını tanımlayıp, *nasıl* yapacağını alt sınıflara bırakmaktır. Soyut sınıflardan (`abstract class`) `new` ile doğrudan nesne üretilemez.
* **Projemizdeki Gerçek Kod:**
  `Vehicle` sınıfımızda her aracın yakıt/enerji tüketim mantığı farklı olduğu için `showFuelEfficiency()` metodunu gövdesiz (`abstract`) olarak tanımladık. Böylece bu sınıftan türeyen tüm alt sınıfları kendi tüketim hesaplamalarını yazmaya zorladık:

```java
// Vehicle.java içindeki soyut metot tanımımız:
public abstract void showFuelEfficiency();

// ElectricCar.java içinde bu soyut metodun doldurulması:
@Override
public void showFuelEfficiency() {
    System.out.println(getBrand() + " " + getModel() + " ortalama 16.5 kWh/100km elektrik tüketir.");
}
```

---

### 5. Interface (Arayüz / Sözleşme) & Casting
> **Teorik Tanım:** Sınıfların uygulamak zorunda olduğu "yetenek sözleşmeleri"dir. Java'da çoklu kalıtım (multiple inheritance) olmadığı için farklı sınıflara ortak yetenekler kazandırmak amacıyla kullanılır.
* **Projemizdeki Gerçek Kod:**
  Araç kiralanabilme yetenekleri için `Rentable`, bakım gereksinimleri için ise `Maintainable` arayüzlerini oluşturduk. Kamyon (`Truck`) sınıfımız `Maintainable` arayüzünü uyguladı. `main.java` içinde `instanceof` kontrolü ve **Interface Casting** yaparak kamyonumuzun kilometresini (12.000 km) denetledik ve 10.000 km sınırını aştığı için otomatik bakıma aldık:

```java
// Maintainable.java arayüzümüz:
public interface Maintainable {
    boolean isNeedsMaintenance();
    void performMaintenance();
}

// Truck.java içindeki arayüz implementasyonumuz:
@Override
public boolean isNeedsMaintenance() {
    return totalKm > 10000;
}

// main.java içindeki gerçek Interface Casting kodumuz:
if (vehicle instanceof Maintainable) {
    Maintainable maintainableVehicle = (Maintainable) vehicle;

    if (maintainableVehicle.isNeedsMaintenance()) {
        AuditService.logTransaction(vehicle.getVehicleId() + " için kilometre sınırı aşılmış, bakım gerekiyor!");
        maintainableVehicle.performMaintenance();
    }
}
```

---

### 6. Method Overriding (Metot Ezme)
> **Teorik Tanım:** Alt sınıfın, ata sınıftan miras aldığı bir metodu kendi yapısına göre yeniden yazarak değiştirmesidir.
* **Projemizdeki Gerçek Kod:**
  `Rentable` arayüzünden gelen `calculateRentalCost` metodunu, her alt sınıfımız kendi iş kurallarına göre ezen (`@Override`) farklı algoritmalarla donattı. Örneğin `Car` sınıfı Sunroof farkı (günde 50 TL) eklerken, `Truck` sınıfı tonaj başına ek ücret (tonaj * 150 TL * gün) ekledi:

```java
// Car.java içindeki gerçek ezilen metot:
@Override
public double calculateRentalCost(int days) {
    double total = getBaseDailyRate() * days;
    if (hasSunroof){
        total += (50 * days); // Sunroof varsa günlük 50 TL eklenir
    }
    return total;
}

// Truck.java içindeki gerçek ezilen metot:
@Override
public double calculateRentalCost(int days) {
    double total = (getBaseDailyRate() * days) + (loadCapacityInTons * 150 * days);
    return total;
}
```

---

### 7. Method Overloading (Aşırı Yükleme)
> **Teorik Tanım:** Aynı sınıf içinde, **aynı metot ismini** kullanarak fakat **parametre sayılarını veya tiplerini değiştirerek** birden fazla metot oluşturmaktır.
* **Projemizdeki Gerçek Kod:**
  `FleetManager` sınıfımızda `printRentalContract` metodumuzu aşırı yükledik. Standart kiralama sözleşmesi için 2 parametreli (`vehicle, days`), kurumsal indirimli sözleşmeler için ise 3 parametreli (`vehicle, days, discountRate`) aynı isimli metotlar hazırladık:

```java
// FleetManager.java içindeki aşırı yüklenen metotlar:
public void printRentalContract(Vehicle vehicle, int days) { ... } // Standart

public void printRentalContract(Vehicle vehicle, int days, double discountRate) { ... } // Kurumsal İndirimli

// main.java içindeki çağrılarımız:
manager.printRentalContract(myCar, 3);          // 2 parametreli metot tetiklenir (3150.0 TL hesaplar)
manager.printRentalContract(myTesla, 5, 10.0);  // 3 parametreli metot tetiklenir (10462.5 TL hesaplar)
```

---

### 8. Final Class, Final Method & Private Constructor (Utility Pattern)
> **Teorik Tanım:** `final` kelimesi bir sınıfın miras alınmasını, bir metodun ise alt sınıflarca ezilmesini engeller. `private constructor` ise bir sınıftan `new` anahtar kelimesiyle nesne türetilmesini yasaklar.
* **Projemizdeki Gerçek Kod:**
  Sistem loglarını tutan `AuditService` sınıfımızı bir **Utility (Yardımcı) Sınıf** olarak tasarladık. Sınıfı `final` yaparak miras alınmasını engelledik ve constructor'ını `private` yaparak `new AuditService()` şeklinde nesne türetilmesini kilitledik. Ayrıca `Vehicle.java` içindeki `startEngine()` metodumuzu güvenlik sebebiyle `final` tanımlayarak alt sınıfların bu motor çalıştırma protokolünü ezmesini yasakladık:

```java
// AuditService.java içindeki Utility Pattern kodumuz:
public final class AuditService {
    private AuditService() {
        // Utility class - dışarıdan nesne üretilemez!
    }
    public static void logTransaction(String message) {
        System.out.println("[SİSTEM LOG] : " + message);
    }
}

// Vehicle.java içindeki final metot örneğimiz:
public final void startEngine(){
    System.out.println("Motor güvenlik kontrolleri yapıldı. " + getBrand() + " " + getModel() + " çalıştırıldı.");
}
```

---

### 9. Static Variables, Static Methods & Static Nested Class
> **Teorik Tanım:** `static` üyelere erişmek için sınıftan nesne üretmeye gerek yoktur; doğrudan sınıf adı üzerinden erişilirler. Uygulama çalıştığı an belleğe (Heap / Static alanına) yüklenirler.
* **Projemizdeki Gerçek Kod:**
  `Vehicle` sınıfımızda oluşturulan her aracı saymak için `public static int totalVehiclesInFleet = 0;` sayacını kurduk ve constructor içinde arttırdık. Ayrıca `FleetManager` içinde garaj koordinatlarını tutan statik bir dahili sınıf (`GPSLocator`) oluşturduk. Hepsine nesne üretmeden doğrudan ulaştık:

```java
// FleetManager.java içindeki statik dahili sınıfımız:
public static class GPSLocator {
    public static String getGarageCoordinates() {
        return "41.0082° N, 28.9784° E (İstanbul Merkez Filo Garajı)";
    }
}

// main.java içindeki gerçek statik çağrı satırlarımız:
System.out.println("Filodaki Toplam Araç Sayısı : " + Vehicle.totalVehiclesInFleet);
System.out.println("Garaj Merkez Konumu         : " + FleetManager.GPSLocator.getGarageCoordinates());
```

---

## Konsol Çıktısı (Terminal Output)

Sistem çalıştırıldığında (`main.java`), araç sayacının doğru çalıştığı, 12.000 km'deki Mercedes Actros kamyonun otomatik bakıma alındığı, Renault Megane için Sunroof ücretinin eklendiği ve kiralama encapsulation kontrollerinin devreye girdiği gerçek konsol çıktımız aşağıdadır:

```text
[SİSTEM LOG] : Akıllı Filo Yönetim Sistemi Başlatılıyor...

--- FİLO GENEL BİLGİLERİ ---
Filodaki Toplam Araç Sayısı : 3
Garaj Merkez Konumu         : 41.0082° N, 28.9784° E (İstanbul Merkez Filo Garajı)
--------------------------------------------------

Motor güvenlik kontrolleri yapıldı. Renault Megane çalıştırıldı.
Renault Megane ortalama 6.5L/100km yakıt tüketir.
--------------------------------------------------
Motor güvenlik kontrolleri yapıldı. Mercedes-Benz Actros çalıştırıldı.
Mercedes-Benz Actros ortalama 25L/100km yakıt tüketir.
[SİSTEM LOG] : TRK-999 için kilometre sınırı aşılmış, bakım gerekiyor!
Kamyon bakıma alındı, yağ ve frenler değişti.
--------------------------------------------------
Motor güvenlik kontrolleri yapıldı. Tesla Model Y çalıştırıldı.
Tesla Model Y ortalama 16.5 kWh/100km elektrik tüketir.
--------------------------------------------------

==============================================
            STANDART KİRALAMA SÖZLEŞMESİ      
==============================================
Araç Bilgisi : Renault Megane
Araç ID      : CAR-101
Kiralama     : 3 Gün
TOPLAM TUTAR : 3150.0 TL
----------------------------------------------
Renault Megane başarıyla kiralandı.
==============================================

==============================================
      İNDİRİMLİ KURUMSAL KİRALAMA SÖZLEŞMESİ  
==============================================
Araç Bilgisi : Tesla Model Y
Araç ID      : ELC-404
Kiralama     : 5 Gün
İndirim Oranı: %10.0
Normal Tutar : 11625.0 TL
İNDİRİMLİ    : 10462.5 TL
----------------------------------------------
Tesla Model Y başarıyla kiralandı.
==============================================

Zaten kirada olan aracı tekrar kiralamayı deniyoruz:
HATA: Renault Megane zaten kirada!
[GÜVENLİK UYARISI] : CAR-101 ID'li araç için güvenlik protokolü ve motor kilidi tetiklendi!
Renault Megane başarıyla teslim alındı.
[SİSTEM LOG] : Sistem Günlükleri Kapatıldı. İyi Günler!
```
