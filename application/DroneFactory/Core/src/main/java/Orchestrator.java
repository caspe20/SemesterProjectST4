
import events.AGVEvent;
import events.AssemblyStationEvent;
import events.WarehouseEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import services.IAGV;
import services.IAssemblyStation;
import services.IWarehouse;


@Component
public class Orchestrator implements ApplicationEventPublisherAware {

    private int warehouseEventId;
    private int agvEventId;
    private int assemblyStationEventId;

    private static Orchestrator orchestrator = null;

    private Orchestrator() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.sdu");
        context.refresh();

        IWarehouse warehouse = context.getBean(IWarehouse.class);

        IAGV agv = context.getBean(IAGV.class);

        IAssemblyStation assemblyStation = context.getBean(IAssemblyStation.class);

        boolean producing = true;


//        while (producing) {
//            switch (MESEventHandler.getInstance())
//        }
    }




    public static Orchestrator getInstance() {
        if (orchestrator == null)
            orchestrator = new Orchestrator();

        return orchestrator;
    }

    public void setWarehouseEventId(int warehouseEventId) {
        this.warehouseEventId = warehouseEventId;
    }

    public void setAgvEventId(int agvEventId) {
        this.agvEventId = agvEventId;
    }

    public void setAssemblyStationEventId(int assemblyStationEventId) {
        this.assemblyStationEventId = assemblyStationEventId;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

    }
}
