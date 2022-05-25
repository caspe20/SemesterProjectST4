import Services.IAGV;
import Services.IAssemblyStation;
import Services.IUIService;
import Services.IWarehouse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.scan("com.sdu");
        context.refresh();

        IAGV agv;
        IWarehouse warehouse;
        IAssemblyStation assemblyStation;
        IUIService ui;

        agv = context.getBean(IAGV.class);
        warehouse = context.getBean(IWarehouse.class);
        assemblyStation = context.getBean(IAssemblyStation.class);
        ui = context.getBean(IUIService.class);

        ui.run(args);

        // UI update
        Thread t = new Thread(()->{
            while(true){
                try {
                    String json = "";
                    JSONObject obj = new JSONObject().put("test", 20);
                    // Get update to ui
                    ui.update(obj.toString());
                    Thread.sleep(100);
                } catch (InterruptedException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
