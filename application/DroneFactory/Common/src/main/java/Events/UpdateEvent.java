package Events;

import org.springframework.context.ApplicationEvent;

public class UpdateEvent {
    private String message;

    public UpdateEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

