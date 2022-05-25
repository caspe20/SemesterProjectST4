package services;

public interface IPublisher {
    // remember if shit hits the fan, then do the final string message
    // baeldung.com/spring-events
    void publishEvent(String message);
}
