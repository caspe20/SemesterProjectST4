import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

class AssemblyStation{
    private MQTTAsyncListener actionListener = new MQTTAsyncListener("Test", "emulation/operation", "userContext");


    private class MQTTAsyncListener implements IMqttActionListener{
        protected final String messageText;
        protected final String topic;
        protected final String userContext;

        private MQTTAsyncListener(String messageText, String topic, String userContext) {
            this.messageText = messageText;
            this.topic = topic;
            this.userContext = userContext;
        }

        @Override
        public void onSuccess(IMqttToken asyncActionToken) {
            if ((asyncActionToken != null) && asyncActionToken.getUserContext().equals(userContext)) {
                System.out.printf("Message '%s' published to topic '%s'%n", messageText, topic);
            }
        }

        @Override
        public void onFailure(
                IMqttToken asyncActionToken,
                Throwable exception) {
            exception.printStackTrace();
        }

    }
}
