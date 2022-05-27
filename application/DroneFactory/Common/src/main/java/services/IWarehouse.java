package services;

public interface IWarehouse {
    String getInventory();
    String getState();
    void dispensePart(int trayId);
    void insertDrone(int trayId);
}
