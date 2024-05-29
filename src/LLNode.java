import java.util.concurrent.atomic.AtomicInteger;

public class LLNode {
    static AtomicInteger idGen = new AtomicInteger(1);

    int id;
    String resource;
    LLNode next;
    LLNode(String resource, LLNode next) {
        this.id = idGen.getAndIncrement();
        this.resource = resource;
        this.next = next;
    }

    public boolean hasNext() {
        return this.next!=null;
    }

    public void addNext(String message) {
        this.next = new LLNode(message, null);
    }
}
