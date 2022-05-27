package helperclasses;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;

public class HazelcastConnection {

    private static final String HAZELCAST_HOST = "127.0.0.1";
    private static final int HAZELCAST_PORT = 5701;
    private final HazelcastInstance hazelcastInstance;

    public HazelcastConnection() {
        String hazelcastAddress = String.format("%s:%d", HAZELCAST_HOST, HAZELCAST_PORT);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress(hazelcastAddress);
        this.hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);
    }

    public void publish(String message, String topicName) {
        ITopic<String> topic = hazelcastInstance.getTopic(topicName);
        topic.publish(message);
    }

    public HazelcastInstance getHazelcastInstance() {
        return hazelcastInstance;
    }

}
