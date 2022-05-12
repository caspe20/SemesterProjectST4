package Services;

public interface IAGV {
    void getState();
    void getTimeStamp();
    void getProgramName();
    void pickupPart();
    void pickupDrone();
    void putdownPart();
    void putdownDrone();
    void goToAssembly();
    void goToWarehouse();
    void goToCharger();
    boolean isCharging();
    int getBatteryPercentage();
}
