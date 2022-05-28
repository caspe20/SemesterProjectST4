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

    private final HazelcastConnection hazelcastConnection;

    public MESEventHandler() {
        hazelcastConnection = new HazelcastConnection();
        warehouseEvent = new WarehouseEvent(0);
        agvEvent = new AGVEvent(0);
        assemblyStationEvent = new AssemblyStationEvent(0);
        productionEvent = new ProductionEvent(0);
        currentProductionEvent = new ProductionEvent(0);
        uiEvent = new UIEvent(0);
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
                case "Warehouse" -> {
                    warehouseEvent = new WarehouseEvent(state);
                    System.out.println("Warehouse: " + warehouseEvent.getEventType().toString());
                    setProductionEvent();
                }
                case "AGV" -> {
                    agvEvent = new AGVEvent(state);
                    System.out.println("AGV: " + agvEvent.getEventType().toString());
                    setProductionEvent();
                }
                case "Assembly Station" -> {
                    assemblyStationEvent = new AssemblyStationEvent(state);
                    System.out.println("Assembly Station: " + assemblyStationEvent.getEventType().toString());
                    setProductionEvent();
                }
                case "UI" -> {
                    uiEvent = new UIEvent(state);
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

        // UI = START_PRODUCTION
        if (uiId == 1) {

            // Warehouse = IDLE, AGV = READY_TO_PICK_UP, Assembly Station = IDLE
            if (warehouseEventId == 1 && agvEventId == 1 && assemblyStationId == 1) {
                // READY_FOR_WAREHOUSE_TO_DISPENSE_PART
                currentProductionEvent = new ProductionEvent(1);

            // Warehouse = IDLE, AGV = READY_TO_PICK_UP
            } else if (warehouseEventId == 1 && agvEventId == 1) {
                // READY_FOR_AGV_TO_PICK_UP_PART
                currentProductionEvent = new ProductionEvent(2);

            // AGV = PART_PICKED_UP
            } else if (agvEventId == 2) {
                // READY_FOR_AGV_TO_MOVE_PART_TO_ASSEMBLY_STATION
                currentProductionEvent = new ProductionEvent(3);

            // AGV = PART_MOVED_TO_ASSEMBLY_STATION, Assembly Station = IDLE
            } else if (agvEventId == 3 && assemblyStationId == 1) {
                // READY_FOR_AGV_TO_DELIVER_PART_TO_ASSEMBLY_STATION
                currentProductionEvent = new ProductionEvent(4);

            // AGV = PART_DELIVERED, Assembly Station = IDLE
            } else if (agvEventId == 4 && assemblyStationId == 1) {
                // READY_FOR_ASSEMBLY_STATION_TO_ASSEMBLE
                currentProductionEvent = new ProductionEvent(5);

            // AGV = READY_TO_PICK_UP_DRONE, Assembly Station = IDLE
            } else if (agvEventId == 111 && assemblyStationId == 1) {
                // READY_FOR_AGV_TO_PICK_UP_DRONE_AT_ASSEMBLY_STATION
                currentProductionEvent = new ProductionEvent(6);

            // AGV = DRONE_PICKED_UP
            } else if (agvEventId == 5) {
                // READY_FOR_AGV_TO_MOVE_DRONE_TO_WAREHOUSE
                currentProductionEvent = new ProductionEvent(7);
            // AGV = DRONE_MOVED_TO_WAREHOUSE, Warehouse = IDLE

            } else if (agvEventId == 6 && warehouseEventId == 1) {
                // READY_FOR_AGV_TO_DELIVER_DRONE_TO_WAREHOUSE
                currentProductionEvent = new ProductionEvent(8);
            // AGV = DRONE_DELIVERED

            } else if (agvEventId == 7)
                // READY_FOR_WAREHOUSE_TO_STORE_DRONE
                currentProductionEvent = new ProductionEvent(9);

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




