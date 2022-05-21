import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import com.sdu.main.AGV;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;
//TODO
// add some kind of delay, mosts tests fail unless run again
// add execute test
    public TestAGV() throws JsonProcessingException {
        agv = new AGV();
    }
    public void before() {
    }
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sdu.main.AGV;
import org.hibernate.query.criteria.internal.expression.function.CurrentTimestampFunction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAGV {
    private AGV agv;
    public void init() throws JsonProcessingException {
    @BeforeEach
        agv = new AGV();
    }
    @Test
    public void getState() {
        if (agv.getState().equals("1") || agv.getState().equals("2") || agv.getState().equals("3")) {
            assertTrue(test);
            test = true;
        }
        assertTrue(test);
    }
    public void getProgramName() {
        System.out.println(agv.getProgramName());
        assertNotNull(agv.getProgramName());
    }
    public void getBatteryPer() {
        boolean test = false;
        if (agv.getBatteryPercentage() <= 100) {
            test = true;
            assertTrue(true);
        }
    }
        assertTrue(test);
    public void isCharging() {
        boolean isCharging = false;
        if (agv.getState() == "3") {
            isCharging = true;
        }
            assertTrue(isCharging);
        assertFalse(isCharging);
    }
    public void pickUpDrone() {
        agv.pickUpDrone();
        assertTrue(Objects.equals(agv.getProgramName(), "\"PickAssemblyOperation\""));
        //Needs to be string literal... because json
    }
    public void putDownDrone() {
        agv.putDownDrone();
        assertTrue(Objects.equals(agv.getProgramName(), "\"PutWarehouseOperation\""));
    }
    public void putDownPart() {
        agv.putDownPart();
        assertTrue(Objects.equals(agv.getProgramName(), "\"PutAssemblyOperation\""));
    }
    public void pickUpPart() {
        agv.pickUpPart();
        assertTrue(Objects.equals(agv.getProgramName(), "\"PickWarehouseOperation\""));
    }
    public void goToAssembly() {
        agv.goToAssembly();
        assertTrue(Objects.equals(agv.getProgramName(), "\"MoveToAssemblyOperation\""));
    }
    public void goToWarehouse() throws InterruptedException {
        agv.goToWarehouse();
        Thread.sleep(5000);
        assertTrue(Objects.equals(agv.getProgramName(), "\"MoveToStorageOperation\""));
    }
    public void goToCharger() throws InterruptedException {
        agv.goToCharger();
        Thread.sleep(500);
            System.out.println("AGV is charging");
        if (agv.getBatteryPercentage() > 100) {
            assertTrue(agv.isCharging());
        } else {
            assertTrue(Objects.equals(agv.getProgramName(), "\"MoveToChargerOperation\""));
            System.out.println("AGV has finished charging");
        }
    }
// The method works, but the timezone is wrong. Look into this.
    @Test
    public void getTimeStamp() {


        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX");
        //Date date1 = new Date();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        System.out.println(sdf2.format(timestamp));

        System.out.println(agv.getTimeStamp());

//        System.out.println(now.getHour() + ":" + now.getMinute() + ":" + now.getSecond());
//        String current = now.getHour() + ":" + now.getMinute() + ":" + now.getSecond();
//        System.out.println(current);
//        System.out.println(cal.getTime());
        //System.out.println(cal.setTimeZone());
     //   assertSame(agv.getTimeStamp(), cal);
    }
    @AfterEach
    public void execute() {
    }
    @AfterAll
    }
    public static void tearDown() {
}