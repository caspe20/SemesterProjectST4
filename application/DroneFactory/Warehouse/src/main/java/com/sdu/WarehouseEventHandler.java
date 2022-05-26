package com.sdu;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import events.ProductionEvent;
import helperclasses.HazelcastConnection;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WarehouseEventHandler {

    WarehouseClient warehouseClient = WarehouseClient.getInstance();
    private final HazelcastConnection hazelcastConnection = new HazelcastConnection();
    private String currentEvent;


    public WarehouseEventHandler()  {
    }

    public void subscribe(String topicName) {
        HazelcastInstance hz = hazelcastConnection.getHazelcastInstance();
        ITopic<String> topic = hz.getTopic(topicName);
        topic.addMessageListener(new MessageListenerImpl());
        System.out.println("Subscribed to " + topicName);
    }

    protected class MessageListenerImpl implements MessageListener<String> {
        public void onMessage(Message<String> m) {
            System.out.println("current event: " + currentEvent);
            System.out.println(m.getMessageObject());
            if (!currentEvent.equals(m.getMessageObject())) {
                System.out.println("Diff");
                switch (m.getMessageObject()) {
                    case "READY_FOR_WAREHOUSE_TO_DISPENSE_PART" -> warehouseClient.dispensePart(warehouseClient.getTrayIdNextPart());
                    case "READY_FOR_WAREHOUSE_TO_STORE_DRONE" -> warehouseClient.insertDrone(warehouseClient.getTrayIdNextDrone());
                }
                currentEvent = m.getMessageObject();
            }
        }
    }
}
