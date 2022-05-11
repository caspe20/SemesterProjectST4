package com.sdu.main;

import Events.UpdateEvent;
import Services.IPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AGVPublisher implements IPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    AGVPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishEvent(final String message) {
        applicationEventPublisher.publishEvent(new UpdateEvent("Hey!"));
    }
}
