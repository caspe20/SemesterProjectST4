package Events;

import org.springframework.context.ApplicationEvent;

public class AGVEvent extends ApplicationEvent {

    public enum EventType{
        READY_TO_PICK_UP(0),
        PART_PICKED_UP(1),
        PART_MOVED_TO_ASSEMBLY_STATION(2),
        DRONE_PICKED_UP(3),
        MOVED_TO_WAREHOUSE(4);

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