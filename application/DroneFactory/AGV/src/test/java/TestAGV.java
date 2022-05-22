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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAGV {

    private AGV agv;
    boolean test;

    //Det er denne Mette har kaldt "before()"
    @BeforeEach
    public void init() throws JsonProcessingException {
        agv = new AGV();
    }

    @Test
    public void getState() {
        if (agv.getState().equals("1") || agv.getState().equals("2") || agv.getState().equals("3")) {
            test = true;
            assertTrue(test);

        }
        assertTrue(test);
    }

    @Test
    public void getProgramName() {
        System.out.println(agv.getProgramName());
        assertNotNull(agv.getProgramName());
    }


    //Den nederste assertTrue, er jeg ikke sikker på hører til her - det skal vi lige tjekke op på.
    @Test
    public void getBatteryPer() {
        test = false;
        if (agv.getBatteryPercentage() <= 100) {
            test = true;
            assertTrue(true);
        }
        assertTrue(test);
    }


    @Test
    public void isCharging() {
        boolean isCharging = false;
        if (agv.getState() == "3") {
            isCharging = true;
            assertTrue(isCharging);
        }
        assertFalse(isCharging);
    }

    @Test
    public void pickUpDrone() {
        agv.pickUpDrone();
        assertTrue(Objects.equals(agv.getProgramName(), "\"PickAssemblyOperation\""));
        //Needs to be string literal... because json
    }

    @Test
    public void putDownDrone() {
        agv.putDownDrone();
        assertTrue(Objects.equals(agv.getProgramName(), "\"PutWarehouseOperation\""));
    }

    @Test
    public void putDownPart() {
        agv.putDownPart();
        assertTrue(Objects.equals(agv.getProgramName(), "\"PutAssemblyOperation\""));
    }

    @Test
    public void pickUpPart() {
        agv.pickUpPart();
        assertTrue(Objects.equals(agv.getProgramName(), "\"PickWarehouseOperation\""));
    }

    @Test
    public void goToAssembly() {
        agv.goToAssembly();
        assertTrue(Objects.equals(agv.getProgramName(), "\"MoveToAssemblyOperation\""));
    }

    @Test
    public void goToWarehouse() throws InterruptedException {
        agv.goToWarehouse();
        Thread.sleep(5000);
        assertTrue(Objects.equals(agv.getProgramName(), "\"MoveToStorageOperation\""));
    }

    @Test
    public void goToCharger() throws InterruptedException {
        agv.goToCharger();
        if (agv.getBatteryPercentage() > 100) {
            System.out.println("AGV is charging");
            assertTrue(agv.isCharging());
        } else {
            assertTrue(Objects.equals(agv.getProgramName(), "\"MoveToChargerOperation\""));
            System.out.println("AGV has finished charging");
        }
    }

    // The method works, but the timezone is wrong. Look into this.
    @Test
    public void getTimeStamp() {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(date.format(timestamp));

        // udprint af det ønskede format:
        System.out.println(agv.getTimeStamp());

        // Selve testen
        //   assertSame(agv.getTimeStamp(), cal);
    }

    @Test
    public void execute() {
        agv.execute();
        if (agv.getState() == "3") {
            assertTrue(test);
        } else {
            assertFalse(test);
        }
    }

    @Test
    public void testConnection() {
        final RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8082/v1/status/", String.class);
        assertNotNull(responseEntity);
    }

    @AfterAll
    public static void tearDown() {

    }

}