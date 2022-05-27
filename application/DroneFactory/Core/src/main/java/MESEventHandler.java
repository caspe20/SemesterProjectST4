import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.internal.json.Json;
import com.hazelcast.internal.json.JsonObject;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import events.*;
import helperclasses.HazelcastConnection;

public class MESEventHandler {

    private UIEvent uiEvent;
    private WarehouseEvent warehouseEvent;
    private AGVEvent agvEvent;
    private AssemblyStationEvent assemblyStationEvent;
    private ProductionEvent productionEvent;
    private ProductionEvent currentProductionEvent;
    private String[] eventSequence = new String[] {"AGVMoveToWarehouse","WarehouseDispenseItem"};

    private int sequenceID = 0;

    private final HazelcastConnection hazelcastConnection;

    public MESEventHandler() {
        hazelcastConnection = new HazelcastConnection();
        warehouseEvent = new WarehouseEvent(this, 0);
        agvEvent = new AGVEvent(this, 0);
        assemblyStationEvent = new AssemblyStationEvent(this, 0);
        productionEvent = new ProductionEvent(this, 0);
        currentProductionEvent = new ProductionEvent(this, 0);
        uiEvent = new UIEvent(this, 0);
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

            System.out.println();

            switch (system) {
                case "Warehouse" -> {
                    warehouseEvent = new WarehouseEvent(this, state);
                    System.out.println("Warehouse: " + warehouseEvent.getEventType().toString());
                    setProductionEvent();
                }
                case "AGV" -> {
                    agvEvent = new AGVEvent(this, state);
                    System.out.println("AGV: " + agvEvent.getEventType().toString());
                    setProductionEvent();
                }
                case "Assembly Station" -> {
                    assemblyStationEvent = new AssemblyStationEvent(this, state);
                    System.out.println("Assembly Station: " + assemblyStationEvent.getEventType().toString());
                    setProductionEvent();
                }
                case "UI" -> {
                    uiEvent = new UIEvent(this, state);
                    System.out.println("UI: " + uiEvent.getEventType().toString());
                    setProductionEvent();
                }
            }
        }
    }

    public void setProductionEvent() {
        int warehouseEventId = warehouseEvent.getEventType().getEventId();
        int agvEventId = agvEvent.getEventType().getEventId();
        int assemblyStationId = assemblyStationEvent.getEventType().getEventId();
        int uiId = uiEvent.getEventType().getEventId();

        // If UI sends start event
        if (uiId == 1) {



            currentProductionEvent = new ProductionEvent(this,sequenceID++);

            // If Warehouse is IDLE, AGC is READY_TO_PICK_UP, Assembly Station is IDLE
            if (warehouseEventId == 1 && agvEventId == 1 && assemblyStationId == 1) {
                // Sets current production event to READY_FOR_WAREHOUSE_TO_DISPENSE_PART
                currentProductionEvent = new ProductionEvent(this, 1);
            // If Warehouse is IDLE and AGV is READY_TO_PICK_UP
            } else if (warehouseEventId == 1 && agvEventId == 1) {
                currentProductionEvent = new ProductionEvent(this, 2);
            } else if (agvEventId == 2) {
                currentProductionEvent = new ProductionEvent(this, 3);
            } else if (agvEventId == 3 && assemblyStationId == 1) {
                currentProductionEvent = new ProductionEvent(this, 4);
            } else if (agvEventId == 4 && assemblyStationId == 1) {
                currentProductionEvent = new ProductionEvent(this, 5);
            } else if (agvEventId == 111 && assemblyStationId == 1) {
                currentProductionEvent = new ProductionEvent(this, 6);
            } else if (agvEventId == 5) {
                currentProductionEvent = new ProductionEvent(this, 7);
            } else if (agvEventId == 6 && warehouseEventId == 1) {
                currentProductionEvent = new ProductionEvent(this, 8);
            } else if (agvEventId == 7)
                currentProductionEvent = new ProductionEvent(this, 9);

            warehouseEventId = -1;
            agvEventId = -1;
            assemblyStationId = -1;

            System.out.println("Old production event: " + productionEvent.getEventType().toString());
            System.out.println("Current production event: " + currentProductionEvent.getEventType().toString());

            if (productionEvent.getEventType() != currentProductionEvent.getEventType()) {
                hazelcastConnection.publish(currentProductionEvent.toString(), "MES");
                productionEvent = currentProductionEvent;
                System.out.println("Published production event: " + currentProductionEvent.getEventType().toString());
            }
        }

    }

    public static void main(String[] args) {
        MESEventHandler mesEventHandler = new MESEventHandler();
        mesEventHandler.subscribe("Assets");
        mesEventHandler.subscribe("UI");
    }
}




