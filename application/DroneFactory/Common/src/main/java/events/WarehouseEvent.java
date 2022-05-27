package events;

import com.hazelcast.internal.json.JsonObject;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Component
public class WarehouseEvent extends ApplicationEvent {

    public enum EventType{
        WAITING(-1),

        STARTING(0),

        IDLE(1),
        EXECUTING(2),
        ERROR(3),
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

    public WarehouseEvent(Object source, int eventId) {
        super(source);
        this.eventType = EventType.getEventType(eventId);
    }

    public EventType getEventType() {
        return eventType;
    }

    @Override
    public String toString() {
        JsonObject jsonEvent = new JsonObject();
        jsonEvent.add("System", "Warehouse");
        jsonEvent.add("State", getEventType().getEventId());
        return jsonEvent.toString();
    }
}