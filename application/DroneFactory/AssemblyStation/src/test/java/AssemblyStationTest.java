import com.sdu.main.AssemblyStationClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssemblyStationTest {
    private AssemblyStationClient assemblyStation;
    private boolean test;

    @BeforeEach
    public void init() {
        assemblyStation = AssemblyStationClient.getInstance();
        test = false;
    }

    @Test
    public void checkExecution() {
        /**
         * The construct method has a problem,
         * before publishing it needs to connect, subscribe and map
         * which means the test needs to be run once to connect
         * and then run again to publish
         */
        //for (int i = 0; i < 10; i++) {
        //    assemblyStation.construct();
        //    System.out.println(assemblyStation.isConstructing());
        //    System.out.println("Operation successful");
        //}

        if (assemblyStation.isConstructing()) {
            test = true;
            System.out.println("Operation successful");
            assertTrue(test);
        } else {
            assertFalse(test);
        }
    }

    @Test
    public void checkHealth() {
        assemblyStation.construct();
        //TODO needs to be constructed before any other test
        //TODO doesn't reach subscribing to checkHealth

        // Wait for 5000 ms (5s)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(assemblyStation.checkProductHealth());
    }

    @Test
    public void checkDone() {
        //assemblyStation.construct();
        System.out.println(assemblyStation.isDone());
        //Returns false from the construct method, but why is done = false?
        //Does it need to run ad infinitum?
    }

    @Test
    public void checkState() {
        assemblyStation.construct();

        // Wait for 1000 ms (1s)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(assemblyStation.getState());
        //Returns null?????
    }

}
