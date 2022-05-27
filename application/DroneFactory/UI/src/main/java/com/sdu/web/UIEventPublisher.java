package com.sdu.web;

import com.hazelcast.core.Hazelcast;
import events.UIEvent;
import helperclasses.HazelcastConnection;

public class UIEventPublisher {

    private HazelcastConnection hazelcastConnection;

    public UIEventPublisher() {
        hazelcastConnection = new HazelcastConnection();
    }

    public void publish (String topic) {
        UIEvent uiEvent = new UIEvent(1);
        hazelcastConnection.publish(uiEvent.toString(), topic);
    }

    public static void main(String[] args) {
        UIEventPublisher uiEventPublisher = new UIEventPublisher();
        uiEventPublisher.publish("UI");
    }

}
