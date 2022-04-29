package example;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class PutSecondTry {

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8082/v1/status/";

        HashMap<String, Object> request = new HashMap<>();
        request.put("Program name", "MoveToChargerOperation");
        request.put("State", 1);

        restTemplate.put(resourceUrl, request);

        JSONObject executeTest = new JSONObject();
        executeTest.put("State", 2);
        restTemplate.put(resourceUrl, executeTest);

    }

}
