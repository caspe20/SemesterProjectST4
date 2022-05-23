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
                ui.run(args);
        }

        System.out.println("test");

        for (IAssemblyStation agv :
                context.getBeansOfType(IAssemblyStation.class).values()) {
            System.out.println("test2");
            agv.construct();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(agv.getState());
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
