public class Producer {
    public void produceToTopic(Topic topic, String message) throws InterruptedException {
        topic.addMessage(message);
    }
}
