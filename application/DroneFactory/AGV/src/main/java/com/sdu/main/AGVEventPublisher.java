package com.sdu.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import events.AGVEvent;
import helperclasses.HazelcastConnection;

public class AGVEventPublisher implements Runnable {

    private final HazelcastConnection hazelcastConnection = new HazelcastConnection();

    private final AGVClient agv = AGVClient.getInstance();

    public AGVEventPublisher() throws JsonProcessingException {
        while (true) {
            try {
                String state = agv.getState();
                String programName = agv.getProgramName();
                int batteryLevel = agv.getBatteryPercentage();
                System.out.println(state);
                System.out.println(programName);

                hazelcastConnection.publish("TEST","AGV");


                if(state.equals("1") && batteryLevel < 20) {
                    AGVEvent batteryLevelLow = new AGVEvent(this, 98);
                    hazelcastConnection.publish(batteryLevelLow.getEventType().toString(), "AGV");
                }

                if(state.equals("1") && programName.equals("\"no program loaded\"")) {
                    AGVEvent readyToPickUp = new AGVEvent(this, 1);
                    hazelcastConnection.publish(readyToPickUp.getEventType().toString(), "AGV");
                }

                if (state.equals("1") && batteryLevel >= 20) {
                    switch (programName) {
                        case "PickWarehouseOperation" -> {
                            AGVEvent partPickedUp = new AGVEvent(this, 2);
                            hazelcastConnection.publish(partPickedUp.getEventType().toString(), "AGV");
                        }
                        case "MoveToAssemblyOperation" -> {
                            AGVEvent partMovedToAssemblyStation = new AGVEvent(this, 3);
                            hazelcastConnection.publish(partMovedToAssemblyStation.getEventType().toString(), "AGV");
                        }
                        case "PutAssemblyOperation" -> {
                            AGVEvent partDelivered = new AGVEvent(this, 4);
                            hazelcastConnection.publish(partDelivered.getEventType().toString(), "AGV");
                            AGVEvent readyToPickUp = new AGVEvent(this, 1);
                            hazelcastConnection.publish(readyToPickUp.getEventType().toString(), "AGV");
                        }
                        case "PickAssemblyOperation" -> {
                            AGVEvent dronePickedUp = new AGVEvent(this, 5);
                            hazelcastConnection.publish(dronePickedUp.getEventType().toString(), "AGV");
                        }
                        case "MoveToStorageOperation" -> {
                            AGVEvent droneMovedToWarehouse = new AGVEvent(this, 6);
                            hazelcastConnection.publish(droneMovedToWarehouse.getEventType().toString(), "AGV");
                        }
                        case "PutWarehouseOperation" -> {
                            AGVEvent droneDelivered = new AGVEvent(this, 7);
                            hazelcastConnection.publish(droneDelivered.getEventType().toString(), "AGV");
                            AGVEvent readyToPickUp = new AGVEvent(this, 1);
                            hazelcastConnection.publish(readyToPickUp.getEventType().toString(), "AGV");
                        }
                    }
                }

                if (state.equals("2")) {
                    switch (programName) {
                        case "PickWarehouseOperation" -> {
                            AGVEvent pickingUpPart = new AGVEvent(this, 8);
                            hazelcastConnection.publish(pickingUpPart.getEventType().toString(), "AGV");
                        }
                        case "MoveToAssemblyOperation" -> {
                            AGVEvent movingToAssemblyStation = new AGVEvent(this, 9);
                            hazelcastConnection.publish(movingToAssemblyStation.getEventType().toString(), "AGV");
                        }
                        case "PutAssemblyOperation" -> {
                            AGVEvent deliveringPart = new AGVEvent(this, 10);
                            hazelcastConnection.publish(deliveringPart.getEventType().toString(), "AGV");
                        }
                        case "PickAssemblyOperation" -> {
                            AGVEvent pickingUpDrone = new AGVEvent(this, 11);
                            hazelcastConnection.publish(pickingUpDrone.getEventType().toString(), "AGV");
                        }
                        case "MoveToStorageOperation" -> {
                            AGVEvent movingToWarehouse = new AGVEvent(this, 12);
                            hazelcastConnection.publish(movingToWarehouse.getEventType().toString(), "AGV");
                        }
                        case "PutWarehouseOperation" -> {
                            AGVEvent deliveringDrone = new AGVEvent(this, 13);
                            hazelcastConnection.publish(deliveringDrone.getEventType().toString(), "AGV");
                        }
                    }
                }

                if (state.equals("3")) {
                    AGVEvent charging = new AGVEvent(this, 99);
                    hazelcastConnection.publish(charging.getEventType().toString(), "AGV");
                }


                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String state = agv.getState();
                String programName = agv.getProgramName();
                int batteryLevel = agv.getBatteryPercentage();
                System.out.println(state);
                System.out.println(programName);

                hazelcastConnection.publish("TEST","AGV");


                if(state.equals("1") && batteryLevel < 20) {
                    AGVEvent batteryLevelLow = new AGVEvent(this, 98);
                    hazelcastConnection.publish(batteryLevelLow.getEventType().toString(), "AGV");
                }

                if(state.equals("1") && programName.equals("\"no program loaded\"")) {
                    AGVEvent readyToPickUp = new AGVEvent(this, 1);
                    hazelcastConnection.publish(readyToPickUp.getEventType().toString(), "AGV");
                }

                if (state.equals("1") && batteryLevel >= 20) {
                    switch (programName) {
                        case "PickWarehouseOperation" -> {
                            AGVEvent partPickedUp = new AGVEvent(this, 2);
                            hazelcastConnection.publish(partPickedUp.getEventType().toString(), "AGV");
                        }
                        case "MoveToAssemblyOperation" -> {
                            AGVEvent partMovedToAssemblyStation = new AGVEvent(this, 3);
                            hazelcastConnection.publish(partMovedToAssemblyStation.getEventType().toString(), "AGV");
                        }
                        case "PutAssemblyOperation" -> {
                            AGVEvent partDelivered = new AGVEvent(this, 4);
                            hazelcastConnection.publish(partDelivered.getEventType().toString(), "AGV");
                            AGVEvent readyToPickUp = new AGVEvent(this, 1);
                            hazelcastConnection.publish(readyToPickUp.getEventType().toString(), "AGV");
                        }
                        case "PickAssemblyOperation" -> {
                            AGVEvent dronePickedUp = new AGVEvent(this, 5);
                            hazelcastConnection.publish(dronePickedUp.getEventType().toString(), "AGV");
                        }
                        case "MoveToStorageOperation" -> {
                            AGVEvent droneMovedToWarehouse = new AGVEvent(this, 6);
                            hazelcastConnection.publish(droneMovedToWarehouse.getEventType().toString(), "AGV");
                        }
                        case "PutWarehouseOperation" -> {
                            AGVEvent droneDelivered = new AGVEvent(this, 7);
                            hazelcastConnection.publish(droneDelivered.getEventType().toString(), "AGV");
                            AGVEvent readyToPickUp = new AGVEvent(this, 1);
                            hazelcastConnection.publish(readyToPickUp.getEventType().toString(), "AGV");
                        }
                    }
                }

                if (state.equals("2")) {
                    switch (programName) {
                        case "PickWarehouseOperation" -> {
                            AGVEvent pickingUpPart = new AGVEvent(this, 8);
                            hazelcastConnection.publish(pickingUpPart.getEventType().toString(), "AGV");
                        }
                        case "MoveToAssemblyOperation" -> {
                            AGVEvent movingToAssemblyStation = new AGVEvent(this, 9);
                            hazelcastConnection.publish(movingToAssemblyStation.getEventType().toString(), "AGV");
                        }
                        case "PutAssemblyOperation" -> {
                            AGVEvent deliveringPart = new AGVEvent(this, 10);
                            hazelcastConnection.publish(deliveringPart.getEventType().toString(), "AGV");
                        }
                        case "PickAssemblyOperation" -> {
                            AGVEvent pickingUpDrone = new AGVEvent(this, 11);
                            hazelcastConnection.publish(pickingUpDrone.getEventType().toString(), "AGV");
                        }
                        case "MoveToStorageOperation" -> {
                            AGVEvent movingToWarehouse = new AGVEvent(this, 12);
                            hazelcastConnection.publish(movingToWarehouse.getEventType().toString(), "AGV");
                        }
                        case "PutWarehouseOperation" -> {
                            AGVEvent deliveringDrone = new AGVEvent(this, 13);
                            hazelcastConnection.publish(deliveringDrone.getEventType().toString(), "AGV");
                        }
                    }
                }

                if (state.equals("3")) {
                    AGVEvent charging = new AGVEvent(this, 99);
                    hazelcastConnection.publish(charging.getEventType().toString(), "AGV");
                }


                    Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public void publishEvent(Object event) {
//
//    }

//    @Override
//    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
//        this.publisher = publisher;
//    }
}
