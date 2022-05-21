package Events;

import org.springframework.context.ApplicationEvent;

public class AssemblyStationEvent extends ApplicationEvent {

    public enum EventType{
        READY_TO_ASSEMBLY(0),
        ASSEMBLING_DONE(1),
        READY_TO_RECEIVE_PART(2),
        READY_TO_DISPENSE_DRONE(3);

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

    public AssemblyStationEvent(Object source, int eventId) {
        super(source);
        this.eventType = EventType.getEventType(eventId);
    }

    public EventType getEventType() {
        return eventType;
    }
}