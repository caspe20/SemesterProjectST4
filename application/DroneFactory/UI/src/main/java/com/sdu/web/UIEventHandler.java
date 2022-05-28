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

    private final JsonObject obj;
    private final UIApplication app;

    public UIEventHandler(String topic,UIApplication app) {
        this.app = app;
        this.hazelcastConnection = new HazelcastConnection();
        subscribe(topic);
        obj = new JsonObject()
                .add("warehouse", new JsonObject().add("state",0).add("connection","disconnected"))
                .add("assemblystation",new JsonObject().add("state",0).add("connection","disconnected"))
                .add("agv",new JsonObject().add("state",0).add("connection","disconnected"));
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
                    ProductionEvent productionEvent = new ProductionEvent(state);
                    obj.set("mes",new JsonObject().add("state", productionEvent.getEventType().toString()));
                }
                case "Warehouse" -> {
                    WarehouseEvent warehouseEvent = new WarehouseEvent(state);
                    obj.set("warehouse",new JsonObject().add("state", warehouseEvent.getEventType().toString()).add("connection","connected"));
                    sendToApplication(obj.toString());
                }
                case "AGV" -> {
                    AGVEvent agvEvent = new AGVEvent(state);
                    obj.set("agv",new JsonObject().add("state", agvEvent.getEventType().toString()).add("connection","connected"));
                    sendToApplication(obj.toString());
                }
                case "Assembly Station" -> {
                    AssemblyStationEvent assemblyStationEvent = new AssemblyStationEvent(state);
                    obj.set("assemblystation",new JsonObject().add("state", assemblyStationEvent.getEventType().toString()).add("connection","connected"));
                    sendToApplication(obj.toString());
                }
            }
        }
    }

    private void sendToApplication(String json){
        app.update(json);
    }

}
