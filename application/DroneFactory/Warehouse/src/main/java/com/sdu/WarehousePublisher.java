package com.sdu;

import events.AssemblyStationEvent;
import helperclasses.HazelcastConnection;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class WarehousePublisher implements Runnable { //, ApplicationEventPublisherAware {

    private final WarehouseClient warehouseClient;
    private final HazelcastConnection hazelcastConnection = new HazelcastConnection();

    public WarehousePublisher(WarehouseClient assemblyStationClient) {
        this.warehouseClient = assemblyStationClient;

    }

    @Override
    public void run() {
        while (true) {
            try {
                String state = warehouseClient.getState();

                switch (state) {
                    case "Idle" -> {
                        AssemblyStationEvent idle = new AssemblyStationEvent(this, 1);
                        hazelcastConnection.publish(idle.getEventType().toString(), "Warehouse");
                    }
                    case "Executing" -> {
                        AssemblyStationEvent constructing = new AssemblyStationEvent(this, 2);
                        hazelcastConnection.publish(constructing.getEventType().toString(), "Warehouse");

                    }
                    case "Error" -> {
                        AssemblyStationEvent error = new AssemblyStationEvent(this, 3);
                        hazelcastConnection.publish(error.getEventType().toString(), "Warehouse");
                    }
                }

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
