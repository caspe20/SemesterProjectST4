package Events;

import org.springframework.context.ApplicationEvent;

public class WarehouseEvent extends ApplicationEvent {

    public enum EventType{
        READY_TO_DISPENSE_PART(0),
        READY_TO_STORE_DRONE(1),
        PART_READY_FOR_PICKUP(2);

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
}