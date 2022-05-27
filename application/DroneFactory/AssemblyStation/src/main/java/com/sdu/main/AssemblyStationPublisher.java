package com.sdu.main;

import events.AGVEvent;
import events.AssemblyStationEvent;
import helperclasses.HazelcastConnection;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

public class AssemblyStationPublisher implements Runnable { //, ApplicationEventPublisherAware {

    private final HazelcastConnection hazelcastConnection;
    private final AssemblyStationClient assemblyStationClient;

    public AssemblyStationPublisher(AssemblyStationClient assemblyStationClient) {
        this.assemblyStationClient = assemblyStationClient;
        this.hazelcastConnection = new HazelcastConnection();
    }

    @Override
    public void run() {
        while (true) {
            try {
                String state = assemblyStationClient.getState();
                switch (state) {
                    case "Idle" -> {
                        AssemblyStationEvent idle = new AssemblyStationEvent(1);
                        hazelcastConnection.publish(idle.toString(), "Assets");
                    }
                    case "Executing" -> {
                        AssemblyStationEvent constructing = new AssemblyStationEvent(2);
                        hazelcastConnection.publish(constructing.toString(), "Assets");
                    }
                    case "Error" -> {
                        AssemblyStationEvent error = new AssemblyStationEvent(3);
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
