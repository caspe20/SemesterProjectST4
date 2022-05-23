package com.sdu.main;

import Services.IAssemblyStation;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AssemblyStationClient implements IAssemblyStation {

    MqttClient client;
    int lastOperation;
    int currentOperation;
    String state;
    String timestamp;
    private boolean constructing = false;
    private boolean done = false;
    boolean lastProductWasHealthy = true;
    private int batchID = 0;


    public AssemblyStationClient() {
        // Establish connection
        try {
            this.client = new MqttClient("tcp://localhost:1883", "1");
            // Establish connection with mqtt client
            client.connect();
            // Subscribes to status and defines function to do with published data
            client.subscribe("emulator/status", (topic, msg) -> {
                // Maps mqtt message into a Json object
                JSONObject obj = new JSONObject(msg.toString());
                // Maps current state to this object
                System.out.println(obj);

                switch ((int) obj.get("State")) {
                    case 0:
                        state = "Idle";
                        break;
                    case 1:
                        state = "Executing";
                        break;
                    case 2:
                        state = "Error";
                        break;
                }
                // Maps last id to this object
                lastOperation = (int) obj.get("LastOperation");

                // Maps current machine state to this object
                currentOperation = (int) obj.get("CurrentOperation");

                // Maps current time to this object
                timestamp = (String) obj.get("TimeStamp");
            });
            System.out.println("1");
            // Subscribes to check health and execute the lambda function to see if the produced product is healthy
            client.subscribe("emulator/checkhealth", (topic, msg) -> {
                // Interprets the json file
                System.out.println("ey!");
                String[] jsonResponse = msg.toString().replaceAll("[{}' \"]","").split(":");
                lastProductWasHealthy = jsonResponse[1].equals("true");
                done = true;
                constructing = false;
            });
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void construct() {
        try {
            client.publish("emulator/operation",
                    new MqttMessage(new JSONObject()
                            .put("ProcessID", batchID++).toString().getBytes()));
            constructing = true;
            done = false;
        } catch (MqttException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkProductHealth() {
        return lastProductWasHealthy;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public boolean isConstructing() {
        return constructing;
    }

    @Override
    public String getState() {
        return state;
    }

    public boolean isLastProductWasHealthy() {
        return lastProductWasHealthy;
    }
}
