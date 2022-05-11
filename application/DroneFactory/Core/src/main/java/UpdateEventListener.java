import Events.UpdateEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;



@Component
public class UpdateEventListener {

    @EventListener
    public void handleRegistration(UpdateEvent event){
        System.out.println("Registration event got triggered for customer::  " + event.getMessage());
    }
}