package com.sdu.main;

import events.AGVEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class AGVEventPublisher implements Runnable { //, ApplicationEventPublisher {//, ApplicationEventPublisherAware {



    private final AGV agv;
    private final ApplicationEventPublisher publisher;

    public AGVEventPublisher(AGV agv) {
        System.out.println("AGVEventPublisher");
        this.agv = agv;
        this.publisher = new ApplicationEventPublisher() {
            @Override
            public void publishEvent(Object event) {
            }
        };
    }

    @Override
    public void run() {
        while (true) {

            try {
                String state = agv.getState();
                String programName = agv.getProgramName();
                int batteryLevel = agv.getBatteryPercentage();
//                System.out.println(state);
//                System.out.println(programName);
//                System.out.println(batteryLevel);
                publisher.publishEvent(new AGVEvent(this, 2));

                if(state.equals("1") && batteryLevel < 20) {
                    AGVEvent batteryLevelLow = new AGVEvent(this, 98);
                    publisher.publishEvent(batteryLevelLow);
                }

                if (state.equals("1") && batteryLevel >= 20) {
                    switch (programName) {
                        case "PickWarehouseOperation" -> {
                            AGVEvent partPickedUp = new AGVEvent(this, 2);
                            publisher.publishEvent(partPickedUp);
                        }
                        case "MoveToAssemblyOperation" -> {
                            AGVEvent partMovedToAssemblyStation = new AGVEvent(this, 3);
                            publisher.publishEvent(partMovedToAssemblyStation);
                        }
                        case "PutAssemblyOperation" -> {
                            AGVEvent partDelivered = new AGVEvent(this, 4);
                            publisher.publishEvent(partDelivered);
                            AGVEvent readyToPickUp = new AGVEvent(this, 1);
                            publisher.publishEvent(readyToPickUp);
                        }
                        case "PickAssemblyOperation" -> {
                            AGVEvent dronePickedUp = new AGVEvent(this, 5);
                            publisher.publishEvent(dronePickedUp);
                        }
                        case "MoveToStorageOperation" -> {
                            AGVEvent droneMovedToWarehouse = new AGVEvent(this, 6);
                            publisher.publishEvent(droneMovedToWarehouse);
                        }
                        case "PutWarehouseOperation" -> {
                            AGVEvent droneDelivered = new AGVEvent(this, 7);
                            publisher.publishEvent(droneDelivered);
                            AGVEvent readyToPickUp = new AGVEvent(this, 1);
                            publisher.publishEvent(readyToPickUp);
                        }
                    }
                }

                if (state.equals("2")) {
                    switch (programName) {
                        case "PickWarehouseOperation" -> {
                            AGVEvent pickingUpPart = new AGVEvent(this, 8);
                            publisher.publishEvent(pickingUpPart);
                        }
                        case "MoveToAssemblyOperation" -> {
                            AGVEvent movingToAssemblyStation = new AGVEvent(this, 9);
                            publisher.publishEvent(movingToAssemblyStation);
                        }
                        case "PutAssemblyOperation" -> {
                            AGVEvent deliveringPart = new AGVEvent(this, 10);
                            publisher.publishEvent(deliveringPart);
                        }
                        case "PickAssemblyOperation" -> {
                            AGVEvent pickingUpDrone = new AGVEvent(this, 11);
                            publisher.publishEvent(pickingUpDrone);
                        }
                        case "MoveToStorageOperation" -> {
                            AGVEvent movingToWarehouse = new AGVEvent(this, 12);
                            publisher.publishEvent(movingToWarehouse);
                        }
                        case "PutWarehouseOperation" -> {
                            AGVEvent deliveringDrone = new AGVEvent(this, 13);
                            publisher.publishEvent(deliveringDrone);
                        }
                    }
                }

                if (state.equals("3")) {
                    AGVEvent charging = new AGVEvent(this, 99);
                    publisher.publishEvent(charging);
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
