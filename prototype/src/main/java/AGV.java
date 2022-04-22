import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AGV {

    public static void main(String[] args) throws JsonProcessingException {
        //Access to HTTP response
        RestTemplate restTemplate = new RestTemplate();
        String resourceURL = "http://localhost:8082";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(resourceURL + "/v1/status/", String.class);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        //System.out.println(responseEntity);

        //Get JSON values
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseEntity.getBody());

        JsonNode battery = root.path("battery");
        JsonNode program_name = root.path("program name");
        JsonNode state = root.path("state");
        JsonNode timeStamp = root.path("timestamp");
        System.out.println(battery + " " + program_name + " " + state + " " + timeStamp);

    }

}
