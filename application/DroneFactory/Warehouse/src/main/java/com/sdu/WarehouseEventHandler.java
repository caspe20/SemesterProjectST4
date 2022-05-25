package com.sdu;

import events.ProductionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WarehouseEventHandler {

    WarehouseClient warehouseClient = WarehouseClient.getInstance();

    public WarehouseEventHandler()  {
    }

    @EventListener
    public void handleProductionEvent(ProductionEvent productionEvent) {

        switch (productionEvent.getEventType().getEventId()) {
            case 1 -> {
                warehouseClient.dispensePart(warehouseClient.getTrayIdNextPart());
                System.out.println("1");
            }
            case 9 -> {
                warehouseClient.insertDrone(warehouseClient.getTrayIdNextDrone());
                System.out.println("2");
            }
        }
    }
}
