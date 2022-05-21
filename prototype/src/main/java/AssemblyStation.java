import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

class AssemblyStation implements MqttCallback {

    public static void main(String[] args) {
        MqttClient client;

        try {
            client = new MqttClient("tcp://localhost:1883","1");
            MqttConnectOptions options = new MqttConnectOptions();

//            options.setUserName("some name");
//            options.setPassword(new char[]{1,2,3,4});

            System.out.println("Connecting");
            client.connect();
            System.out.println("Connected");
            System.out.println("Subscribing");
            client.subscribe("emulator/status",1);
            client.subscribe("emulator/status",(topic,msg)->{
                System.out.println(topic + ": " + msg);
            });
            client.subscribe("emulator/checkhealth",(topic,msg)->{
                System.out.println(topic + ": " + msg);
            });
            client.publish("emulator/operation",new MqttMessage("{ \"ProcessID\" : 1 }".getBytes()));
            System.out.println("Subscribed");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("yikes");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        System.out.printf("Message '%s' received on topic '%s'", mqttMessage,s);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("I did a good job!");
    }
}
