package com.sdu.main;

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

public class AssemblyStationEventHandler {

    private final AssemblyStationClient assemblyStationClient;
    private final HazelcastConnection hazelcastConnection;
    private ProductionEvent productionEvent;

    public AssemblyStationEventHandler(AssemblyStationClient client, String topic)  {
        this.assemblyStationClient = client;
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
                if (Objects.equals(currentProductionEvent.getEventType().toString(), "READY_FOR_ASSEMBLY_STATION_TO_ASSEMBLE")) {
                    assemblyStationClient.construct();
                }

            }
        }
    }
}
