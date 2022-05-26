package com.sdu.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import helperclasses.HazelcastConnection;

public class AGVEventHandler {

    private final AGVClient agv = AGVClient.getInstance();
    private final HazelcastConnection hazelcastConnection = new HazelcastConnection();
    private String currentEvent;

    public AGVEventHandler() throws JsonProcessingException {
        currentEvent = "No event";
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
                    case "READY_FOR_AGV_TO_PICK_UP_PART" -> agv.pickUpPart();
                    case "READY_FOR_AGV_TO_MOVE_PART_TO_ASSEMBLY_STATION" -> agv.goToAssembly();
                    case "READY_FOR_AGV_TO_DELIVER_PART_TO_ASSEMBLY_STATION" -> agv.putDownPart();
                    case "READY_FOR_AGV_TO_PICK_UP_DRONE_AT_ASSEMBLY_STATION" -> agv.pickUpDrone();
                    case "READY_FOR_AGV_TO_MOVE_DRONE_TO_WAREHOUSE" -> agv.goToWarehouse();
                    case "READY_FOR_AGV_TO_DELIVER_DRONE_TO_WAREHOUSE" -> agv.putDownDrone();
                }
                currentEvent = m.getMessageObject();
            }
        }
    }


//            case 2 -> agv.pickUpPart();
//            case 3 -> agv.goToAssembly();
//            case 4 -> agv.putDownPart();
//            case 6 -> agv.pickUpDrone();
//            case 7 -> agv.goToWarehouse();
//            case 8 -> agv.putDownDrone();


}


