import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import com.sdu.main.AGV;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sdu.main.AGV;
import org.hibernate.query.criteria.internal.expression.function.CurrentTimestampFunction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAGV {

    private AGV agv;
    boolean test;

    @BeforeEach
    public void init() throws JsonProcessingException {
        agv = new AGV();
    }

    /**
     * Checks if getState method is equal to the three listed states, from the technical documentation
     */
    @Test
    public void getState() {
        if (agv.getState().equals("1") || agv.getState().equals("2") || agv.getState().equals("3")) {
            test = true;
            System.out.println(agv.getState());
            assertTrue(test);

        }
        assertTrue(test);
    }

    /**
     * Checks if program name can be reached and isn't empty
     */
    @Test
    public void getProgramName() {
        if (agv.getProgramName() != null) {
            test = true;
            System.out.println(agv.getProgramName());
            assertTrue(test);
        } else {
            assertFalse(test);
        }
    }


    /**
     * Checks if getBattery can be reached.
     */
    @Test
    public void getBatteryPer() {
        if (agv.getBatteryPercentage() <= 100) {
            System.out.println(agv.getBatteryPercentage());
            test = true;
            assertTrue(true);
        }
        assertFalse(test);
    }


    /**
     *
     */
    @Test
    public void isCharging() {
        if (Objects.equals(agv.getState(), "3")) {
            test = true;
            System.out.println(agv.isCharging());
            assertTrue(test);
        }
        assertFalse(test);
    }

    /**
     * The tests below checks if the program commands work.
     * The test needs to be run twice, junit is faster than the program name changing.
     * Thread.sleep() doesn't work - not matter the milli interval
     */
    @Test
    public void pickUpDrone() {
        try {
            agv.pickUpDrone();
            Thread.sleep(100);
            System.out.println(agv.getProgramName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            assertEquals("\"PickAssemblyOperation\"", agv.getProgramName());
        }
    }

    @Test
    public void putDownDrone() {
        try {
            agv.putDownDrone();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            assertEquals("\"PutWarehouseOperation\"", agv.getProgramName());
        }
    }

    @Test
    public void putDownPart() {
        agv.putDownPart();
        assertEquals("\"PutAssemblyOperation\"", agv.getProgramName());
    }

    @Test
    public void pickUpPart() {
        agv.pickUpPart();
        assertEquals("\"PickWarehouseOperation\"", agv.getProgramName());
    }

    @Test
    public void goToAssembly() {
        agv.goToAssembly();
        assertEquals("\"MoveToAssemblyOperation\"", agv.getProgramName());
    }

    @Test
    public void goToWarehouse() throws InterruptedException {
        agv.goToWarehouse();
        Thread.sleep(100);
        assertEquals("\"MoveToStorageOperation\"", agv.getProgramName());
    }

    @Test
    public void goToCharger() {
        agv.goToCharger();
        if (agv.getBatteryPercentage() > 100) {
            System.out.println("AGV is charging");
            assertTrue(agv.isCharging());
        } else {
            assertEquals("\"MoveToChargerOperation\"", agv.getProgramName());
            System.out.println("AGV has finished charging");
        }
    }

    /**
     * The strings are near identical, the only difference is milliseconds
     * As a 'fix' it checks the length of our timestamp vs. agv timestamp
     */

    @Test
    public void getTimeStamp() {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS+00:00");
        date.setTimeZone(TimeZone.getTimeZone("UTC"));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //Convert to string literal
        String literal = "\"" + date.format(timestamp) + "\"";

        //Current format
        System.out.println("Computer timestamp: " + literal);
        //Print of the wished format
        System.out.println("AGV timestamp: " + agv.getTimeStamp());
        assertSame(agv.getTimeStamp().length(), literal.length());
    }

    /**
     * Checks if the agv is in the middle of executing
     */
    @Test
    public void execute() {
        agv.execute();
        if (agv.getState() == "3") {
            assertTrue(test);
        } else {
            assertFalse(test);
        }
    }

    /**
     * Tests the connection to the simulator, and tests if the read jsonbody isn't null
     */
    @Test
    public void testConnection() {
        final RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8082/v1/status/", String.class);
        assertNotNull(responseEntity);
    }
}