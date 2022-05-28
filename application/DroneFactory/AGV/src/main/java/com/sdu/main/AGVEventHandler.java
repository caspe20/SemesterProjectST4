package com.sdu.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.internal.json.Json;
import com.hazelcast.internal.json.JsonObject;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import events.ProductionEvent;
import helperclasses.HazelcastConnection;

public class AGVEventHandler {

    private final AGVClient agvClient;
    private final HazelcastConnection hazelcastConnection;
    private ProductionEvent productionEvent;

    public AGVEventHandler(AGVClient client, String topic) throws JsonProcessingException {
        this.agvClient = client;
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
                    case "READY_FOR_AGV_TO_PICK_UP_PART" -> {
                        agvClient.pickUpPart();
                        System.out.println("READY_FOR_AGV_TO_PICK_UP_PART");
                    }
                    case "READY_FOR_AGV_TO_MOVE_PART_TO_ASSEMBLY_STATION" -> agvClient.goToAssembly();
                    case "READY_FOR_AGV_TO_DELIVER_PART_TO_ASSEMBLY_STATION" -> agvClient.putDownPart();
                    case "READY_FOR_AGV_TO_PICK_UP_DRONE_AT_ASSEMBLY_STATION" -> agvClient.pickUpDrone();
                    case "READY_FOR_AGV_TO_MOVE_DRONE_TO_WAREHOUSE" -> agvClient.goToWarehouse();
                    case "READY_FOR_AGV_TO_DELIVER_DRONE_TO_WAREHOUSE" -> agvClient.putDownDrone();
                }

            }
        }
    }
}


