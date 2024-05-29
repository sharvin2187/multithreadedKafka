import java.util.HashMap;
import java.util.Map;

public class Consumer {
    Map<Topic, LLNode> subscribedTopics = new HashMap<>();

    void subscribeToTopic(Topic topic) {
        subscribedTopics.put(topic, topic.getHead());
    }

    // same as resetToHead
    void resetToHead(Topic topic) {
        subscribeToTopic(topic);
    }

    String consumeInTopic(Topic topic) throws InterruptedException {
        LLNode itr =  topic.getNextNode(subscribedTopics.get(topic));
        subscribedTopics.put(topic, itr);
        return itr.resource;
    }
}
