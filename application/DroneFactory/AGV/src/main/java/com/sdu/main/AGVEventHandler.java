package com.sdu.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import events.ProductionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AGVEventHandler {

    AGV agv = AGV.getInstance();

    public AGVEventHandler() throws JsonProcessingException {
    }

    @EventListener
    public void handleProductionEvent(ProductionEvent productionEvent) {
        switch (productionEvent.getEventType().getEventId()) {
            case 2 -> {
                agv.pickUpPart();
                System.out.println("1");
            }
            case 3 -> {
                agv.goToAssembly();
                System.out.println("2");
            }
            case 4 -> {
                agv.putDownPart();
                System.out.println("3");
            }
            case 6 -> {
                agv.pickUpDrone();
                System.out.println("4");
            }
            case 7 -> {
                agv.goToWarehouse();
                System.out.println("5");
            }
            case 8 -> {
                agv.putDownDrone();
                System.out.println("6");
            }

//            case 2 -> agv.pickUpPart();
//            case 3 -> agv.goToAssembly();
//            case 4 -> agv.putDownPart();
//            case 6 -> agv.pickUpDrone();
//            case 7 -> agv.goToWarehouse();
//            case 8 -> agv.putDownDrone();


        }
    }

}
