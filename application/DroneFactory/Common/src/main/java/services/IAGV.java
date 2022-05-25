package services;

public interface IAGV {
    String getState();
    String getTimeStamp();
    String getProgramName();
    int getBatteryPercentage();

    boolean isCharging();

    void pickUpPart();
    void pickUpDrone();

    void putDownPart();
    void putDownDrone();

    void goToAssembly();
    void goToWarehouse();
    void goToCharger();
}
