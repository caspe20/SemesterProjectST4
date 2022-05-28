package com.sdu.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import events.AGVEvent;
import helperclasses.HazelcastConnection;

public class AGVEventPublisher implements Runnable {

    private final HazelcastConnection hazelcastConnection;
    private final AGVClient agvClient;

    public AGVEventPublisher(AGVClient client) throws JsonProcessingException {
        this.agvClient = client;
        this.hazelcastConnection = new HazelcastConnection();
    }

    @Override
    public void run() {
        while (true) {
            try {
                String state = agvClient.getState();
                String programName = agvClient.getProgramName().replace("\"", "");
                int batteryLevel = agvClient.getBatteryPercentage();

                if (state.equals("1") && batteryLevel < 20) {
                    AGVEvent batteryLevelLow = new AGVEvent(98);
                    hazelcastConnection.publish(batteryLevelLow.toString(), "Assets");
                }

                if (state.equals("1") && programName.equals("no program loaded")) {
                    AGVEvent readyToPickUp = new AGVEvent(0);
                    hazelcastConnection.publish(readyToPickUp.toString(), "Assets");
                }

                if (state.equals("1") && batteryLevel >= 20) {
                    switch (programName) {
                        case "PickWarehouseOperation" -> {
                            AGVEvent partPickedUp = new AGVEvent(2);
                            hazelcastConnection.publish(partPickedUp.toString(), "Assets");
                        }
                        case "MoveToAssemblyOperation" -> {
                            AGVEvent partMovedToAssemblyStation = new AGVEvent(3);
                            hazelcastConnection.publish(partMovedToAssemblyStation.toString(), "Assets");
                        }
                        case "PutAssemblyOperation" -> {
                            AGVEvent partDelivered = new AGVEvent(4);
                            hazelcastConnection.publish(partDelivered.toString(), "Assets");
                            Thread.sleep(2000);
                            AGVEvent readyToPickUpDrone = new AGVEvent(111);
                            hazelcastConnection.publish(readyToPickUpDrone.toString(), "Assets");
                        }
                        case "PickAssemblyOperation" -> {
                            AGVEvent dronePickedUp = new AGVEvent(5);
                            hazelcastConnection.publish(dronePickedUp.toString(), "Assets");
                        }
                        case "MoveToStorageOperation" -> {
                            AGVEvent droneMovedToWarehouse = new AGVEvent(6);
                            hazelcastConnection.publish(droneMovedToWarehouse.toString(), "Assets");
                        }
                        case "PutWarehouseOperation" -> {
                            AGVEvent droneDelivered = new AGVEvent(7);
                            hazelcastConnection.publish(droneDelivered.toString(), "Assets");
                        }
                    }
                }

                if (state.equals("2")) {
                    switch (programName) {
                        case "PickWarehouseOperation" -> {
                            AGVEvent pickingUpPart = new AGVEvent(8);
                            hazelcastConnection.publish(pickingUpPart.toString(), "Assets");
                        }
                        case "MoveToAssemblyOperation" -> {
                            AGVEvent movingToAssemblyStation = new AGVEvent(9);
                            hazelcastConnection.publish(movingToAssemblyStation.toString(), "Assets");
                        }
                        case "PutAssemblyOperation" -> {
                            AGVEvent deliveringPart = new AGVEvent(10);
                            hazelcastConnection.publish(deliveringPart.toString(), "Assets");
                        }
                        case "PickAssemblyOperation" -> {
                            AGVEvent pickingUpDrone = new AGVEvent(11);
                            hazelcastConnection.publish(pickingUpDrone.toString(), "Assets");
                        }
                        case "MoveToStorageOperation" -> {
                            AGVEvent movingToWarehouse = new AGVEvent(12);
                            hazelcastConnection.publish(movingToWarehouse.toString(), "Assets");
                        }
                        case "PutWarehouseOperation" -> {
                            AGVEvent deliveringDrone = new AGVEvent(13);
                            hazelcastConnection.publish(deliveringDrone.toString(), "Assets");
                        }
                    }
                }

                if (state.equals("3")) {
                    AGVEvent charging = new AGVEvent(99);
                    hazelcastConnection.publish(charging.toString(), "Assets");
                }

                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
