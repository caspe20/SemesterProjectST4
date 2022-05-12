package com.sdu.main;

import Services.IAGV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class AGV implements IAGV {
    private final String resourceUrl = "http://localhost:8082/v1/status/";
    private RestTemplate restTemplate = new RestTemplate();

    private String resourceURL1 = "http://localhost:8082";
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(resourceURL1 + "/v1/status/", String.class);

    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root = mapper.readTree(responseEntity.getBody());

    public AGV() {

    }

    //Execute method - OBS is needed for every method
    public void execute() {
        JSONObject executeTest = new JSONObject();
        executeTest.put("State", 2);
        restTemplate.put(resourceUrl, executeTest);
    }

    @Override
    public void getState() {
        JsonNode state = root.path("state");
        return state;
    }

    @Override
    public void getTimeStamp() {
        JsonNode timeStamp = root.path("timestamp");
        return timeStamp;
    }

    @Override
    public void getProgramName() {
        JsonNode program_name = root.path("program name");
        return program_name;
    }

    @Override
    public void pickupPart() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "PickWarehouseOperation");
        request.put("State", 1);
        restTemplate.put(resourceUrl, request);
        execute();
    }

    @Override
    public void pickupDrone() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "PickAssemblyOperation");
        request.put("State", 1);
        restTemplate.put(resourceUrl, request);
        execute();
    }

    @Override
    public void putdownPart() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "PutAssemblyOperation");
        request.put("State", 1);
        restTemplate.put(resourceUrl, request);
        execute();
    }

    @Override
    public void putdownDrone() {
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

    @Override
    public boolean isCharging() {
        return false;
    }

    @Override
    public int getBatteryPercentage() {
        return 0;
    }
}