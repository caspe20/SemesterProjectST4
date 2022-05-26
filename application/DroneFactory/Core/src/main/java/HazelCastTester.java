import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;
import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import helperclasses.HazelcastConnection;

public class HazelCastTester {

    private final HazelcastConnection hazelcastConnection = new HazelcastConnection();


    public HazelCastTester() {
    }

    public void subscribe(String topicName) {
        HazelcastInstance hz = hazelcastConnection.getHazelcastInstance();
        ITopic<String> topic = hz.getTopic(topicName);
        topic.addMessageListener(new MessageListenerImpl());
        System.out.println("Subscribed to " + topicName);
    }

    protected class MessageListenerImpl implements MessageListener<String> {
        public void onMessage(Message<String> m) {
            switch (m.getMessageObject()) {
                case "READY_TO_PICK_UP" -> hazelcastConnection.publish("READY_FOR_AGV_TO_PICK_UP_PART", "MES");
            }
            System.out.println(m.getMessageObject());
        }
    }

    public static void main(String[] args) {
        HazelCastTester hazelCastTester = new HazelCastTester();
        hazelCastTester.subscribe("AGV");
    }

}
