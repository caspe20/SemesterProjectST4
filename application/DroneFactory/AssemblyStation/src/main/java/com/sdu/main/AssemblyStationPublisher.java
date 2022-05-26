package com.sdu.main;

import events.AGVEvent;
import events.AssemblyStationEvent;
import helperclasses.HazelcastConnection;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

public class AssemblyStationPublisher implements Runnable { //, ApplicationEventPublisherAware {

    private final AssemblyStationClient assemblyStationClient;
    private final HazelcastConnection hazelcastConnection = new HazelcastConnection();

    public AssemblyStationPublisher(AssemblyStationClient assemblyStationClient) {
        this.assemblyStationClient = assemblyStationClient;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String state = assemblyStationClient.getState();
                switch (state) {
                    case "Idle" -> {
                        AssemblyStationEvent idle = new AssemblyStationEvent(this, 1);
                        hazelcastConnection.publish(idle.getEventType().toString(), "Assembly Station");
                    }
                    case "Executing" -> {
                        AssemblyStationEvent constructing = new AssemblyStationEvent(this, 2);
                        hazelcastConnection.publish(constructing.getEventType().toString(), "Assembly Station");
                    }
                    case "Error" -> {
                        AssemblyStationEvent error = new AssemblyStationEvent(this, 3);
                        hazelcastConnection.publish(error.getEventType().toString(), "Assembly Station");
                    }
                }

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
