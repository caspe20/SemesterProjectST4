import Services.IAGV;
import Services.IUIService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.scan("com.sdu");
        context.refresh();

        for (IUIService ui : context.getBeansOfType(IUIService.class).values()){
            SpringApplication.run(ui.getClass(),args);
        }

        System.out.println("test");

        for (IAGV agv :
                context.getBeansOfType(IAGV.class).values()) {
            System.out.println("test2");
            agv.getState();
        }

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void run(){
    }
}
