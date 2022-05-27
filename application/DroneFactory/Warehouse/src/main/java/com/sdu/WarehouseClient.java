package com.sdu;

import events.WarehouseEvent;
import services.IWarehouse;
import com.sdu.wsdl.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class WarehouseClient implements IWarehouse {

    private int trayIdNextPart = 1;
    private int trayIdNextDrone = 1;

    // Warehouse specific variables
    private WarehouseState state = null;
    private JSONArray inventory;

    // client variables
    private final WebServiceTemplate template;
    final private String host = "http://localhost:8081/Service.asmx";
    private Thread updateThread;


    public WarehouseClient() {

        // Initialize the inventory list
        inventory = new JSONArray();
        state = WarehouseState.getWarehouseState(0);

        // Construct the template communication device of the warehouse client
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.sdu.wsdl");
        template = new WebServiceTemplate();
        template.setMarshaller(marshaller);
        template.setUnmarshaller(marshaller);
        template.setDefaultUri(host);

        // Make update thread.
        updateThread = new Thread(() -> {
            try {
                while (true) {
                    // Get json string of the inventory
                    JSONObject obj = new JSONObject(getInventoryFromWarehouse());
                    if (WarehouseState.getWarehouseState(obj.getInt("State")) != state || !obj.getJSONArray("Inventory").equals(inventory)) {
                        // Update variables
                        state = WarehouseState.getWarehouseState(obj.getInt("State"));
                        inventory = obj.getJSONArray("Inventory");
                        // Make updateEvent happen here:

                        // End event
                    }

                    // Updates every 100ms
                    Thread.sleep(100);
                }
            } catch (InterruptedException | JSONException e) {
                e.printStackTrace();
            }
        });
        updateThread.start();

        Thread warehousePublisherThread = new Thread(new WarehousePublisher(this));
        warehousePublisherThread.start();
        WarehouseEventHandler warehouseEventHandler = new WarehouseEventHandler(this, "MES");
    }

    private String getInventoryFromWarehouse() {
        GetInventoryResponse respone = (GetInventoryResponse) template.marshalSendAndReceive(host, new GetInventory());
        return respone.getGetInventoryResult();
    }

    @Override
    public String getInventory() {
        return inventory.toString();
    }

    @Override
    public String getState() {
        return state.toString();
    }

    @Override
    public void dispensePart(int trayId) {
        PickItem item = new PickItem();
        item.setTrayId(trayId);
        PickItemResponse response = (PickItemResponse) template.marshalSendAndReceive(host, item);
        trayIdNextPart++;
        System.out.println("Part dispensed");
        // send dispensedPart event here
    }

    @Override
    public void insertDrone(int trayId) {
        InsertItem item = new InsertItem();
        item.setTrayId(trayId);
        item.setName("Drone");
        InsertItemResponse response = (InsertItemResponse) template.marshalSendAndReceive(host, item);
        trayIdNextDrone++;
        System.out.println("Drone inserted");
        // Send insertedPart event here
    }

    public int getTrayIdNextPart() {
        return trayIdNextPart;
    }

    public int getTrayIdNextDrone() {
        return trayIdNextDrone;
    }

    public static void main(String[] args) {
        WarehouseClient warehouseClient = new WarehouseClient();
    }


}