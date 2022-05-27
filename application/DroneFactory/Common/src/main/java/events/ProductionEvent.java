package events;

import com.hazelcast.internal.json.JsonObject;

public class ProductionEvent {

    public enum EventType{
        STARTING(0),
        READY_FOR_WAREHOUSE_TO_DISPENSE_PART(1),
        READY_FOR_AGV_TO_PICK_UP_PART(2),
        READY_FOR_AGV_TO_MOVE_PART_TO_ASSEMBLY_STATION(3),
        READY_FOR_AGV_TO_DELIVER_PART_TO_ASSEMBLY_STATION(4),
        READY_FOR_ASSEMBLY_STATION_TO_ASSEMBLE(5),
        READY_FOR_AGV_TO_PICK_UP_DRONE_AT_ASSEMBLY_STATION(6),
        READY_FOR_AGV_TO_MOVE_DRONE_TO_WAREHOUSE(7),
        READY_FOR_AGV_TO_DELIVER_DRONE_TO_WAREHOUSE(8),
        READY_FOR_WAREHOUSE_TO_STORE_DRONE(9),
        PRODUCTION_DONE(10),
        ;

        private final int eventId;

        EventType(int eventId) {
            this.eventId = eventId;
        }

        public int getEventId() {
            return eventId;
        }

        public static EventType getEventType(int i) {
            for (EventType event: EventType.values()) {
                if(event.getEventId() == i) {
                    return event;
                }
            }
            return null;
        }
    }

    private final EventType eventType;

    public ProductionEvent(int eventId) {
        this.eventType = EventType.getEventType(eventId);
    }

    public EventType getEventType() {
        return eventType;
    }

    @Override
    public String toString() {
        JsonObject jsonEvent = new JsonObject();
        jsonEvent.add("System", "MES");
        jsonEvent.add("State", getEventType().getEventId());
        return jsonEvent.toString();
    }
}