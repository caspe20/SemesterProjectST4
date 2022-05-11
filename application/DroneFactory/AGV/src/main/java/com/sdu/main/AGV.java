package com.sdu.main;

import Services.IAGV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AGV implements IAGV {

    @Autowired
    private AGVPublisher publisher;

    @Override
    public void getState() {
        publisher.publishEvent("this is a test, yo!");
    }

    @Override
    public void pickupPart() {

    }

    @Override
    public void pickupDrone() {

    }

    @Override
    public void putdownPart() {

    }

    @Override
    public void putdownDrone() {

    }

    @Override
    public void goToAssembly() {

    }

    @Override
    public void goToWarehouse() {

    }

    @Override
    public void goToCharger() {

    }

    @Override
    public boolean isCharging() {
        return false;
    }

    @Override
    public int getBatteryPercentage() {
        return 0;
    }
}
