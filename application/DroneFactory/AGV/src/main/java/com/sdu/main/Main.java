package com.sdu.main;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        AGVClient agv = AGVClient.getInstance();
        AGVEventPublisher agvEventPublisher = new AGVEventPublisher();
        AGVEventHandler agvEventHandler = new AGVEventHandler();
        agvEventHandler.subscribe("MES");
    }
}
