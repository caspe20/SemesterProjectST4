package Services;

public interface IAGV {
    void getState();
    void getTimeStamp();
    void getProgramName();
    void pickUpPart();
    void pickUpDrone();
    void putDownPart();
    void putDownDrone();
    void goToAssembly();
    void goToWarehouse();
    void goToCharger();
    boolean isCharging();
    int getBatteryPercentage();
}
