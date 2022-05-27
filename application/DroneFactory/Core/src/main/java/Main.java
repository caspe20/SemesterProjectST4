import org.springframework.context.ApplicationEventPublisherAware;
import services.IAGV;
import services.IAssemblyStation;
import services.IUIService;
import services.IWarehouse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.scan("com.sdu");
        context.refresh();


//        IUIService iuiService = context.getBean(IUIService.class);
//        IAGV agv = context.getBean(IAGV.class);
//        IAssemblyStation assemblyStation = context.getBean(IAssemblyStation.class);
//        IWarehouse warehouse = context.getBean(IWarehouse.class);

       // Thread.sleep(2000);




//        for (IAssemblyStation assemblyStation :
//                context.getBeansOfType(IAssemblyStation.class).values()) {
//            assemblyStation.construct();
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Assembly Station: " + assemblyStation.getState());
//        }
//
//        for (IAGV agv :
//                context.getBeansOfType(IAGV.class).values()) {
//            System.out.println("AGV: " + agv.getState());
//        }
//
//        for (IWarehouse warehouse :
//                context.getBeansOfType(IWarehouse.class).values()) {
//            System.out.println("Warehouse: " + warehouse.getState());
//        }
//
//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }






    }
}
