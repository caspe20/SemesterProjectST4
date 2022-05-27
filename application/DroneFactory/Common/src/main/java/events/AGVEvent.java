package events;

import com.hazelcast.internal.json.JsonObject;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AGVEvent extends ApplicationEvent {

    public enum EventType{
        WAITING(-1),
        STARTING(0),

        READY_TO_PICK_UP_PART(1),
        PART_PICKED_UP(2),
        PART_MOVED_TO_ASSEMBLY_STATION(3),
        PART_DELIVERED(4),
        DRONE_PICKED_UP(5),
        DRONE_MOVED_TO_WAREHOUSE(6),
        DRONE_DELIVERED(7),

        PICKING_UP_PART(8),
        MOVING_TO_ASSEMBLY_STATION(9),
        DELIVERING_PART(10),
        PICKING_UP_DRONE(11),
        MOVING_TO_WAREHOUSE(12),
        DELIVERING_DRONE(13),

        READY_TO_PICK_UP_DRONE(111),

        BATTERY_LEVEL_LOW(98),
        CHARGING(99),
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

        public static EventType getEventType(String name) {
            for (EventType event: EventType.values()) {
                if(event.toString() == name) {
                    return event;
                }
            }
            return null;
        }

    }

    private final EventType eventType;

    public AGVEvent(Object source, int eventId) {
        super(source);
        this.eventType = EventType.getEventType(eventId);
    }

    public EventType getEventType() {
        return eventType;
    }

    @Override
    public String toString() {
        JsonObject jsonEvent = new JsonObject();
        jsonEvent.add("System", "AGV");
        jsonEvent.add("State", getEventType().getEventId());
        return jsonEvent.toString();
    }
}