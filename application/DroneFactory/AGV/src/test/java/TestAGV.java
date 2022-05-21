import com.fasterxml.jackson.core.JsonProcessingException;
import com.sdu.main.AGV;
import org.junit.jupiter.api.*;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
//TODO
// add some kind of delay, mosts tests fail unless run again
// add execute test

public class TestAGV {
    private AGV agv;

    public TestAGV() throws JsonProcessingException {
        agv = new AGV();
    }

    @BeforeEach
    public void before() {

    }

    @org.junit.jupiter.api.Test
    public void getProgramName() {
        System.out.println(agv.getProgramName());
        assertNotNull(agv.getProgramName());
    }

    @org.junit.jupiter.api.Test
    public void getBatteryPer() {
        boolean test = false;
        if (agv.getBatteryPercentage() <= 100) {
            test = true;
            assertTrue(true);
        }
        assertTrue(test);
    }

    @org.junit.jupiter.api.Test
    public void isCharging() {
        boolean isCharging = false;
        if (agv.getState() == "3") {
            isCharging = true;
            assertTrue(isCharging);
        }
        assertFalse(isCharging);
    }

    @org.junit.jupiter.api.Test
    public void pickUpDrone() {
        agv.pickUpDrone();
        //Needs to be string literal... because json
        assertTrue(Objects.equals(agv.getProgramName(), "\"PickAssemblyOperation\""));
    }

    @org.junit.jupiter.api.Test
    public void putDownDrone() {
        agv.putDownDrone();
        assertTrue(Objects.equals(agv.getProgramName(), "\"PutWarehouseOperation\""));
    }

    @org.junit.jupiter.api.Test
    public void putDownPart() {
        agv.putDownPart();
        assertTrue(Objects.equals(agv.getProgramName(), "\"PutAssemblyOperation\""));
    }

    @org.junit.jupiter.api.Test
    public void pickUpPart() {
        agv.pickUpPart();
        assertTrue(Objects.equals(agv.getProgramName(), "\"PickWarehouseOperation\""));
    }

    @org.junit.jupiter.api.Test
    public void goToAssembly() {
        agv.goToAssembly();
        assertTrue(Objects.equals(agv.getProgramName(), "\"MoveToAssemblyOperation\""));
    }

    @org.junit.jupiter.api.Test
    public void goToWarehouse() throws InterruptedException {
        agv.goToWarehouse();
        Thread.sleep(5000);
        assertTrue(Objects.equals(agv.getProgramName(), "\"MoveToStorageOperation\""));
    }

    @org.junit.jupiter.api.Test
    public void goToCharger() throws InterruptedException {
        agv.goToCharger();
        Thread.sleep(500);
        if (agv.getBatteryPercentage() > 100) {
            assertTrue(agv.isCharging());
            System.out.println("AGV is charging");
        } else {
            assertTrue(Objects.equals(agv.getProgramName(), "\"MoveToChargerOperation\""));
            System.out.println("AGV has finished charging");
        }
    }
}
