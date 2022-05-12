import com.example.consumingwebservice.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class WarehouseClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(WarehouseClient.class);

    public GetInventoryResponse getInventory() {
        GetInventory request = new GetInventory();
        log.info("Requesting Warehouse inventory");
        GetInventoryResponse response = (GetInventoryResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8081/Service.asmx", request);
        return response;
    }

    public PickItemResponse getItem(int trayId) {
        PickItem request = new PickItem();
        request.setTrayId(trayId);
        log.info("Picking Warehouse item from tray " + trayId);
        PickItemResponse response = (PickItemResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8081/Service.asmx", request);
        return response;
    }

    public InsertItemResponse insertItem(int trayId) {
        InsertItem request = new InsertItem();
        request.setTrayId(trayId);
        log.info("Insert Warehouse item into tray " + trayId);
        InsertItemResponse response = (InsertItemResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8081/Service.asmx", request);
        return response;
    }

    @Override
    public String toString() {
        return "hej";
    }
}
