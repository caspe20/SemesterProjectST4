import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.internal.json.Json;
import com.hazelcast.internal.json.JsonObject;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import helperclasses.HazelcastConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MESTest implements MessageListener<String> {

    private MESTest mesTest;
    private boolean agvConnection = false;
    private boolean warehouseConnection = false;
    private boolean assemblyConnection = false;

    //Is used in addMessageListener
    @Override
    public void onMessage(Message<String> message) {
        JsonObject jsonObject = Json.parse(message.getMessageObject()).asObject();
        String system = jsonObject.get("System").asString().replace("\"", "");
        if (system.equals("Warehouse")) {
            warehouseConnection = true;
        } else if (system.equals("AGV")) {
            agvConnection = true;
        } else if (system.equals("Assembly Station")) {
            assemblyConnection = true;
        } else {
            System.out.println(":(");
        }
    }

    @BeforeEach
    public void init() throws InterruptedException {
        mesTest = new MESTest();

        //Create connection to hazelcast + subscribe to topic
        HazelcastInstance hz = new HazelcastConnection().getHazelcastInstance();
        ITopic<String> topic = hz.getTopic("Assets");
        ITopic<String> topic1 = hz.getTopic("UI");
        topic.addMessageListener(mesTest);
        topic1.addMessageListener(mesTest);
        System.out.println("Subscribed to Assets and UI");

        System.out.println("\n");

        Thread.sleep(6000);
    }

    @Test
    public void integrationAGVTest() {
        assertTrue(mesTest.agvConnection);
    }

    @Test
    public void integrationWarehouseTest() {
        assertTrue(mesTest.warehouseConnection);
    }

    @Test
    public void integrationAssemblyTest() {
        assertTrue(mesTest.assemblyConnection);
    }
}
