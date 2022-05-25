package com.sdu.main;

import events.AGVEvent;
import events.AssemblyStationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class AssemblyStationPublisher implements Runnable { //, ApplicationEventPublisherAware {

    private final AssemblyStationClient assemblyStationClient;
    private final ApplicationEventPublisher publisher;

    public AssemblyStationPublisher(AssemblyStationClient assemblyStationClient) {
        this.assemblyStationClient = assemblyStationClient;
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
                String state = assemblyStationClient.getState();

                switch (state) {
                    case "Idle" -> {
                        AssemblyStationEvent idle = new AssemblyStationEvent(this, 1);
                        publisher.publishEvent(idle);
                    }
                    case "Executing" -> {
                        AssemblyStationEvent constructing = new AssemblyStationEvent(this, 2);
                        publisher.publishEvent(constructing);
                    }
                    case "Error" -> {
                        AssemblyStationEvent error = new AssemblyStationEvent(this, 3);
                        publisher.publishEvent(error);
                    }
                }

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
//        this.publisher = publisher;
//    }
}
