import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Topic> topics = new ArrayList<>();
        topics.add(new Topic());
        topics.add(new Topic());

        Producer p1 = new Producer();
        Producer p2 = new Producer();

        Consumer c1 = new Consumer();
        Consumer c2 = new Consumer();

        c1.subscribeToTopic(topics.get(0));
        c1.subscribeToTopic(topics.get(1));
        c2.subscribeToTopic(topics.get(0));
        c2.subscribeToTopic(topics.get(1));

        new Thread(() -> {
            int count = 40;
            while (count-- > 0) {
                try {
                    Thread.sleep(19);
                    String message = "message(" + Thread.currentThread().getName() + "---" + count + ")";
                    p1.produceToTopic(topics.get(1), message);
                    System.out.println("Produced to topic: t1 : " + message);
                } catch (InterruptedException e) {
                }
            }
        }).start();

        new Thread(() -> {
            int count = 40;
            while (count-- > 0) {
                try {
                    Thread.sleep(19);
                    String message = "message(" + Thread.currentThread().getName() + "---" + (1000 + count) + ")";
                    p2.produceToTopic(topics.get(0), message);
                    System.out.println("Produced to topic: t0 : " + message);
                } catch (InterruptedException e) {
                }
            }
        }).start();


        new Thread(() -> {
            int count = 40;
            while (--count > 0) {
                try {
                    if(count==19) {
                        c1.resetToHead(topics.get(count % 2));
                    }
                    Thread.sleep(15);
                    String message = c1.consumeInTopic(topics.get(count % 2));
                    System.out.println("Consumed from thread: " + Thread.currentThread().getName() + " : " + message);
                } catch (InterruptedException e) {
                }
            }
        }).start();

        new Thread(() -> {
            int count = 40;
            while (--count > 0) {
                try {
                    Thread.sleep(21);
                    String message = c1.consumeInTopic(topics.get(count % 2));
                    System.out.println("Consumed from thread: " + Thread.currentThread().getName() + " : " + message);
                } catch (InterruptedException e) {
                }
            }
        }).start();
    }
}