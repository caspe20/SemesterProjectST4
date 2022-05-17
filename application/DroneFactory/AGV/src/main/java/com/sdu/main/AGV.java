package com.sdu.main;

import Services.IAGV;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.stereotype.Component;
//import org.springframework.http.HttpStatus;

import java.util.HashMap;

@Service
public class AGV implements IAGV {

    public AGV() throws JsonProcessingException {

    }

    private final String resourceUrl = "http://localhost:8082/v1/status/";

    final RestTemplate restTemplate = new RestTemplate();
    final String resourceURL1 = "http://localhost:8082";

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(resourceURL1 + "/v1/status/", String.class);
    final ObjectMapper mapper = new ObjectMapper();

    final JsonNode root = mapper.readTree(responseEntity.getBody());

    //Execute method - OBS is needed for every method
    public void execute() {
        JSONObject executeTest = new JSONObject();
        executeTest.put("State", 2);
        restTemplate.put(resourceUrl, executeTest);
    }



    @Override
    public String getState() {
        JsonNode state = root.path("state");
        return state.toString();
    }

    @Override
    public String getTimeStamp() {
        JsonNode timeStamp = root.path("timestamp");
        return timeStamp.toString();
    }

    @Override
    public String getProgramName() {
        JsonNode programName = root.path("program name");
        return programName.toString();
    }

    @Override
    public int getBatteryPercentage() {
        JsonNode batteryPercentage = root.path("battery");
        return batteryPercentage.asInt();
    }

    @Override
    public boolean isCharging() {
        JsonNode state = root.path("state");
        return state.intValue() == 3;
    }

    @Override
    public void pickUpPart() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "PickWarehouseOperation");
        request.put("State", 1);
        restTemplate.put(resourceUrl, request);
        execute();
    }

    @Override
    public void pickUpDrone() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "PickAssemblyOperation");
        request.put("State", 1);
        restTemplate.put(resourceUrl, request);
        execute();
    }

    @Override
    public void putDownPart() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "PutAssemblyOperation");
        request.put("State", 1);
        restTemplate.put(resourceUrl, request);
        execute();
    }

    @Override
    public void putDownDrone() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "PutWarehouseOperation");
        request.put("State", 1);
        restTemplate.put(resourceUrl, request);
        execute();
    }

    @Override
    public void goToAssembly() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "MoveToAssemblyOperation");
        request.put("State", 1);
        restTemplate.put(resourceUrl, request);
        execute();
    }

    @Override
    public void goToWarehouse() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "MoveToStorageOperation");
        request.put("State", 1);
        restTemplate.put(resourceUrl, request);
        execute();
    }

    @Override
    public void goToCharger() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "MoveToChargerOperation");
        request.put("State", 1);
        restTemplate.put(resourceUrl, request);
        execute();

    }

}