import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Test {


    public static void main(String[] args) {
        ApplicationContext factory = new AnnotationConfigApplicationContext(WarehouseConfiguration.class);
        WarehouseClient warehouseClient = factory.getBean(WarehouseClient.class);
        System.out.println(warehouseClient.getInventory().getGetInventoryResult());
    }

}
