import Services.IAGV;
import Services.IAssemblyStation;
import Services.IUIService;
import Services.IWarehouse;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.scan("com.sdu");
        context.refresh();

        for (IUIService ui : context.getBeansOfType(IUIService.class).values()){
//            Thread t = new Thread(()->{
                ui.run(args);
//            });
//            t.start();
        }

        System.out.println("test");

        for (IAGV agv :
                context.getBeansOfType(IAGV.class).values()) {
            System.out.println("test2");
            agv.getState();
        }

        for (IAssemblyStation assemblyStation :
                context.getBeansOfType(IAssemblyStation.class).values()) {
            System.out.println("test3");
            assemblyStation.getState();
        }

        for (IWarehouse warehouse :
                context.getBeansOfType(IWarehouse.class).values()) {
            System.out.println("test4");
            warehouse.getState();
        }

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
