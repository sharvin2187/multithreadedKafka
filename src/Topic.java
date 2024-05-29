import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Map;

public class Topic {
    ReentrantLock writeLock = new ReentrantLock();
    ReentrantLock endLock = new ReentrantLock();
    Condition endCondition = endLock.newCondition();
    LLNode head = new LLNode("BEGIN", null);
    LLNode tail = head;
    Map<Integer, LLNode> idToNode = new HashMap<>();

    Topic() {
        idToNode.put(head.id, head);
    }

    public void addMessage(String message) throws InterruptedException {
        writeLock.lock();
        tail.addNext(message);
        idToNode.put(tail.id, tail);
        tail = tail.next;
        writeLock.unlock();

        endLock.lock();
        endCondition.signalAll();
        endLock.unlock();
    }

    // itr points to a message that is already consumed
    public LLNode getNextNode(LLNode itr) throws InterruptedException {
        if(itr.id==tail.id) {
            endLock.lock();
            while(itr.id==tail.id) {
                endCondition.await();
            }
            // our copy of previous tail does not have next pointer set, fetch latest
            itr = idToNode.get(itr.id);
            endCondition.signalAll();
            endLock.unlock();
        }

        return itr.next;
    }

    public LLNode getHead() {
        return head;
    }
}
