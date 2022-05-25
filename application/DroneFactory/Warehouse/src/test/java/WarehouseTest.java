import com.sdu.WarehouseClient;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {
    private WarehouseClient warehouseClient, mockWarehouse;
    private boolean test;

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void init() {
        warehouseClient = WarehouseClient.getInstance();
        test = false;
    }

    /**
     * Asserts whether the inventory is full or not + can be reached.
     */
    @Test
    public void checkInventory() {
        sleep();
        if (warehouseClient.getInventory().length() == 2) {
            System.out.println("Inventory is empty: " + warehouseClient.getInventory());
            assertEquals("[]", warehouseClient.getInventory());
        } else {
            System.out.println("Inventory is: " + warehouseClient.getInventory());
            assertNotSame("[]", warehouseClient.getInventory());
        }
    }

    /**
     * Checks if state can be reached, and checks the reached state
     */
    @Test
    public void checkState() {
        sleep();
        if (warehouseClient.getState().equals("Idle") || warehouseClient.getState().equals("Executing") || warehouseClient.getState().equals("Error")) {
            test = true;
            System.out.println("State can be reached.\nState reached: " + warehouseClient.getState());
            assertTrue(test);
        } else {
            System.out.println("State cannot be reached.");
            assertTrue(test);
        }
    }

    /**
     * Dispenses every item from the JsonObject
     * and afterwards prints out the inventory to check
     */
    @Test
    public void checkDispense() {
        for (int i = 0; i < 20; i++) {
            sleep();
            warehouseClient.dispensePart(i);
            System.out.println("Item dispensed");
        }
        checkInventory();
    }

    /**
     * Inserts every slot in JsonObject with a drone
     * and afterwards prints out the inventory
     */
    @Test
    public void checkInsert() {
        for (int i = 0; i < 20; i++) {
            sleep();
            warehouseClient.insertDrone(i);
            System.out.println("Drone inserted");
        }
        checkInventory();
    }


}
