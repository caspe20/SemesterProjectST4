import Events.UpdateEvent;
import Services.IAGV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.scan("com.sdu");
        context.refresh();

        System.out.println("test");

        for (IAGV agv :
                context.getBeansOfType(IAGV.class).values()) {
            System.out.println("test2");
            agv.getState();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
