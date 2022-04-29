package example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class AGVClient {
    private final String resourceUrl = "http://localhost:8082/v1/status/";
    private RestTemplate restTemplate = new RestTemplate();

    private String resourceURL1 = "http://localhost:8082";
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(resourceURL1 + "/v1/status/", String.class);


    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root = mapper.readTree(responseEntity.getBody());

    public AGVClient() throws JsonProcessingException {
    }

    //Methods for Http PUT
    public void moveToAssemblyStation() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "MoveToAssemblyOperation");
        request.put("State", 1);
        restTemplate.put(resourceUrl, request);
        execute();
    }

    public void moveToChargerOperation() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "MoveToChargerOperation");
        request.put("State", 1);
        restTemplate.put(resourceUrl, request);
        execute();
    }

    public void moveToStorageOperation() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "MoveToStorageOperation");
        request.put("State", 1);
        restTemplate.put(resourceUrl, request);
        execute();
    }

    public void putAssemblyOperation() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "PutAssemblyOperation");
        request.put("State", 1);
        restTemplate.put(resourceUrl, request);
        execute();
    }

    public void pickAssemblyOperation() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "PickAssemblyOperation");
        request.put("State", 1);
        restTemplate.put(resourceUrl, request);
        execute();
    }

    public void pickWarehouseOperation() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "PickWarehouseOperation");
        request.put("State", 1);
        restTemplate.put(resourceUrl, request);
        execute();
    }

    public void putWarehouseOperation() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "PutWarehouseOperation");
        request.put("State", 1);
        restTemplate.put(resourceUrl, request);
        execute();
    }

    // Method for executing Http PUT
    public void execute() {
        JSONObject executeTest = new JSONObject();
        executeTest.put("State", 2);
        restTemplate.put(resourceUrl, executeTest);
    }

    public JsonNode getBattery(){
        JsonNode battery = root.path("battery");
        return battery;
    }

    public JsonNode getProgram_name(){
        JsonNode program_name = root.path("program name");
        return program_name;
    }

    public JsonNode getState(){
        JsonNode state = root.path("state");
        return state;
    }

    public JsonNode getTimeStamp(){
        JsonNode timeStamp = root.path("timestamp");
        return timeStamp;
    }

    

}
