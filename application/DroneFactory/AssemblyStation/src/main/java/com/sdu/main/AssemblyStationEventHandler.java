package com.sdu.main;

import events.ProductionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AssemblyStationEventHandler {

    AssemblyStationClient assemblyStationClient = AssemblyStationClient.getInstance();

    public AssemblyStationEventHandler()  {
    }

    @EventListener
    public void handleProductionEvent(ProductionEvent productionEvent) {
        if (productionEvent.getEventType().getEventId() == 5) {
            assemblyStationClient.construct();
            System.out.println("1");
        }
    }
}
