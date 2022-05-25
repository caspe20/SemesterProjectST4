import com.sdu.main.AssemblyStationClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssemblyStationTest {
    private AssemblyStationClient assemblyStation;
    private boolean test;

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Needs to wait at least 100 millis or else it won't work
     * construct is called here to avoid redundancy
     */
    @BeforeEach
    public void init() {
        assemblyStation = new AssemblyStationClient();
        test = false;
        assemblyStation.construct();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void checkExecution() {
        if (assemblyStation.isConstructing()) {
            test = true;
            System.out.println("Construction successful");
            assertTrue(test);
        } else {
            assertFalse(test);
        }
    }

    /**
     * For the methods below:
     * Needs to wait at least 1000 millis due to operation/health in constructor
     * @throws InterruptedException
     */
    @Test
    public void checkHealth() throws InterruptedException {
        Thread.sleep(8000);
        if (assemblyStation.checkProductHealth()) {
            test = true;
            System.out.println("Health successful: " + assemblyStation.checkProductHealth());
            assertTrue(test);
        } else {
            assertFalse(test);
        }
    }

    @Test
    public void checkDone() throws InterruptedException {
        Thread.sleep(9000);
        if (assemblyStation.isDone()) {
            test = true;
            System.out.println("Done successful: " + assemblyStation.isDone());
            assertTrue(test);
        } else {
            assertFalse(test);
        }
    }

    @Test
    public void checkState() {
        // Wait for 1000 ms (1s)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (assemblyStation.getState().equals("Idle") || assemblyStation.getState().equals("Executing") || assemblyStation.getState().equals("Error")) {
            test = true;
            System.out.println("State successful: " + assemblyStation.getState());
            assertTrue(test);
        } else {
            assertFalse(test);
        }
    }
}
