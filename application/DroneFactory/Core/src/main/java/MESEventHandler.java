import events.AGVEvent;
import events.AssemblyStationEvent;
import events.ProductionEvent;
import events.WarehouseEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MESEventHandler implements ApplicationEventPublisherAware {

    private WarehouseEvent warehouseEvent;
    private AGVEvent agvEvent;
    private AssemblyStationEvent assemblyStationEvent;
    private ProductionEvent productionEvent;

    ApplicationEventPublisher publisher;

    private static MESEventHandler mesEventHandler = null;

    private MESEventHandler() {
    }

    public static MESEventHandler getInstance() {
        if (mesEventHandler == null)
            mesEventHandler = new MESEventHandler();
        return mesEventHandler;
    }

    @EventListener
    public void handleWarehouseEvent(WarehouseEvent event) {
        this.warehouseEvent = event;
        System.out.println(event.getEventType());
        setProductionEvent();
    }

    @EventListener
    public void handleAGVEvent(AGVEvent event) {
        this.agvEvent = event;
        System.out.println(event.getEventType());
        setProductionEvent();
    }

    @EventListener
    public void handleAssemblyStationEvent(AssemblyStationEvent event) {
        this.assemblyStationEvent = event;
        System.out.println(event.getEventType());
        setProductionEvent();
    }

    public void setProductionEvent() {
        int warehouseEventId = warehouseEvent.getEventType().getEventId();
        int agvEventId = agvEvent.getEventType().getEventId();
        int assemblyStationId = assemblyStationEvent.getEventType().getEventId();

        System.out.println("HEJ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        if (warehouseEventId == 1) {
            productionEvent = new ProductionEvent(this, 1);
            publisher.publishEvent(productionEvent);
        }
        if (warehouseEventId == 1 && agvEventId == 1) {
            productionEvent = new ProductionEvent(this,2);
            publisher.publishEvent(productionEvent);
        }
        if (agvEventId == 2) {
            productionEvent = new ProductionEvent(this,3);
            publisher.publishEvent(productionEvent);
        }
        if (agvEventId == 3 && assemblyStationId == 1) {
            productionEvent = new ProductionEvent(this,4);
            publisher.publishEvent(productionEvent);
        }
        if (agvEventId == 4 && assemblyStationId == 1) {
            productionEvent = new ProductionEvent(this,5);
            publisher.publishEvent(productionEvent);
        }
        if (agvEventId == 1 && assemblyStationId == 1) {
            productionEvent = new ProductionEvent(this,6);
            publisher.publishEvent(productionEvent);
        }
        if (agvEventId == 5) {
            productionEvent = new ProductionEvent(this,7);
            publisher.publishEvent(productionEvent);
        }
        if (agvEventId == 6 && warehouseEventId == 1) {
            productionEvent = new ProductionEvent(this,8);
            publisher.publishEvent(productionEvent);
        }
        if (agvEventId == 7) {
            productionEvent = new ProductionEvent(this,9);
            publisher.publishEvent(productionEvent);
        }
    }

    public ProductionEvent getProductionEvent() {
        return productionEvent;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}



