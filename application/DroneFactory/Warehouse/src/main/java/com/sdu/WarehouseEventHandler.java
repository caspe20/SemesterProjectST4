package com.sdu;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.internal.json.Json;
import com.hazelcast.internal.json.JsonObject;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import events.ProductionEvent;
import helperclasses.HazelcastConnection;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

public class WarehouseEventHandler {

    private final WarehouseClient warehouseClient;
    private final HazelcastConnection hazelcastConnection;
    private ProductionEvent productionEvent;


    public WarehouseEventHandler(WarehouseClient warehouseClient, String topic)  {
        this.warehouseClient = warehouseClient;
        this.hazelcastConnection = new HazelcastConnection();
        productionEvent = new ProductionEvent(this, 0);
        subscribe(topic);
    }

    public void subscribe(String topicName) {
        HazelcastInstance hz = hazelcastConnection.getHazelcastInstance();
        ITopic<String> topic = hz.getTopic(topicName);
        topic.addMessageListener(new MessageListenerImpl());
        System.out.println("Subscribed to " + topicName);
    }

    protected class MessageListenerImpl implements MessageListener<String> {
        public void onMessage(Message<String> m) {
            JsonObject jsonEvent = Json.parse(m.getMessageObject()).asObject();
            int state = jsonEvent.get("State").asInt();
            ProductionEvent currentProductionEvent = new ProductionEvent(this, state);

            System.out.println();
            System.out.println("Old production event: " + productionEvent.getEventType().toString());
            System.out.println("Current production event " + currentProductionEvent.getEventType().toString());
            System.out.println();

            if (productionEvent.getEventType() != currentProductionEvent.getEventType()) {
                productionEvent = currentProductionEvent;
                switch (currentProductionEvent.getEventType().toString()) {
                    case "READY_FOR_WAREHOUSE_TO_DISPENSE_PART" -> {
                        System.out.println("!!!!!!!!!!!!!!");
                        warehouseClient.dispensePart(warehouseClient.getTrayIdNextPart());
                    }
                    case "READY_FOR_WAREHOUSE_TO_STORE_DRONE" -> {
                        System.out.println("SIMON");
                        warehouseClient.insertDrone(warehouseClient.getTrayIdNextDrone());
                    }
                }
            }
        }
    }
}
