package com.sdu;

import events.WarehouseEvent;
import helperclasses.HazelcastConnection;

public class WarehousePublisher implements Runnable {

    private final HazelcastConnection hazelcastConnection;
    private final WarehouseClient warehouseClient;

    public WarehousePublisher(WarehouseClient warehouseClient) {
        this.warehouseClient = warehouseClient;
        this.hazelcastConnection = new HazelcastConnection();
    }

    @Override
    public void run() {
        while (true) {
            try {
                String state = warehouseClient.getState();

                switch (state) {
                    case "Idle" -> {
                        WarehouseEvent idle = new WarehouseEvent(1);
                        hazelcastConnection.publish(idle.toString(), "Assets");
                    }
                    case "Executing" -> {
                        WarehouseEvent constructing = new WarehouseEvent(2);
                        hazelcastConnection.publish(constructing.toString(), "Assets");
                    }
                    case "Error" -> {
                        WarehouseEvent error = new WarehouseEvent(3);
                        hazelcastConnection.publish(error.toString(), "Assets");
                    }
                }

                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
