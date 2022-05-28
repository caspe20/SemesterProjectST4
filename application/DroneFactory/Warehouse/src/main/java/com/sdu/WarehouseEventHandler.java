package com.sdu;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.internal.json.Json;
import com.hazelcast.internal.json.JsonObject;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import events.AGVEvent;
import events.ProductionEvent;
import helperclasses.HazelcastConnection;

public class WarehouseEventHandler {

    private final WarehouseClient warehouseClient;
    private final HazelcastConnection hazelcastConnection;
    private ProductionEvent productionEvent;


    public WarehouseEventHandler(WarehouseClient warehouseClient, String topic)  {
        this.warehouseClient = warehouseClient;
        this.hazelcastConnection = new HazelcastConnection();
        productionEvent = new ProductionEvent(0);
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
            ProductionEvent currentProductionEvent = new ProductionEvent(state);

            if (productionEvent.getEventType() != currentProductionEvent.getEventType()) {
                productionEvent = currentProductionEvent;
                switch (currentProductionEvent.getEventType().toString()) {
                    case "READY_FOR_WAREHOUSE_TO_DISPENSE_PART" -> {
                        warehouseClient.dispensePart(warehouseClient.getTrayIdNextPart());
                        AGVEvent agvEvent = new AGVEvent(1);
                        hazelcastConnection.publish(agvEvent.toString(), "Assets");
                    }
                    case "READY_FOR_WAREHOUSE_TO_STORE_DRONE" -> {
                        warehouseClient.insertDrone(warehouseClient.getTrayIdNextDrone());
                    }
                }
            }
        }
    }
}
