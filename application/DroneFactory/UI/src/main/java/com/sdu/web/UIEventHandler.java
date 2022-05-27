package com.sdu.web;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.internal.json.Json;
import com.hazelcast.internal.json.JsonObject;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import events.AGVEvent;
import events.AssemblyStationEvent;
import events.ProductionEvent;
import events.WarehouseEvent;
import helperclasses.HazelcastConnection;

public class UIEventHandler {

    private final HazelcastConnection hazelcastConnection;

    private WarehouseEvent warehouseEvent;
    private AGVEvent agvEvent;
    private AssemblyStationEvent assemblyStationEvent;
    private ProductionEvent productionEvent;


    public UIEventHandler(String topic) {
        this.hazelcastConnection = new HazelcastConnection();
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
            String system = jsonEvent.get("System").asString().replace("\"", "");
            int state = jsonEvent.get("State").asInt();

            switch (system) {
                case "MES" -> {
                    productionEvent = new ProductionEvent(this, state);
                    //System.out.println(system + ": " + productionEvent.getEventType());
                }
                case "Warehouse" -> {
                    warehouseEvent = new WarehouseEvent(this, state);
                    System.out.println(system + ": " + warehouseEvent.getEventType());

                }
                case "AGV" -> {
                    agvEvent = new AGVEvent(this, state);
                    System.out.println(system + ": " + agvEvent.getEventType());

                }
                case "Assembly Station" -> {
                    assemblyStationEvent = new AssemblyStationEvent(this, state);
                    System.out.println(system + ": " + assemblyStationEvent.getEventType());

                }
            }
        }
    }

    public static void main(String[] args) {
        UIEventHandler uiEventHandlerMES = new UIEventHandler("MES");
        UIEventHandler uiEventHandlerAssets = new UIEventHandler("Assets");
    }


}
