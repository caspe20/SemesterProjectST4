package Events;

import org.springframework.context.ApplicationEvent;

public class AGVEvent extends ApplicationEvent {

    public enum EventType{
        READY_TO_PICK_UP(0),
        PART_DELIVERED_TO_ASSEMBLY_STATION(1),
        DRONE_MOVED_TO_WAREHOUSE(2),
        STARTED_CHARGING(3),
        CHARGING_DONE(4),
        PART_MOVED_TO_ASSEMBLY_STATION(5);

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

    public AGVEvent(Object source, int eventId) {
        super(source);
        this.eventType = EventType.getEventType(eventId);
    }

    public EventType getEventType() {
        return eventType;
    }
}