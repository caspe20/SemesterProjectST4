package com.sdu;

import events.AssemblyStationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class WarehousePublisher implements Runnable, ApplicationEventPublisher { //, ApplicationEventPublisherAware {

    private final WarehouseClient warehouseClient;
    private final ApplicationEventPublisher publisher;

    public WarehousePublisher(WarehouseClient assemblyStationClient) {
        this.warehouseClient = assemblyStationClient;
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
                String state = warehouseClient.getState();

                switch (state) {
                    case "Idle" -> {
                        AssemblyStationEvent idle = new AssemblyStationEvent(this, 1);
                        publishEvent(idle);
                        System.out.println("TEST");
                    }
                    case "Executing" -> {
                        AssemblyStationEvent constructing = new AssemblyStationEvent(this, 2);
                        publishEvent(constructing);
                    }
                    case "Error" -> {
                        AssemblyStationEvent error = new AssemblyStationEvent(this, 3);
                        publishEvent(error);
                    }
                }

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void publishEvent(Object event) {

    }

//    @Override
//    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
//        this.publisher = publisher;
//    }
}
