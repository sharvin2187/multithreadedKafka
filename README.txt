Attempts to design the following:
- there is a fixed list of topics
- there are some consumers and producers for each topic
- consumers must be able to read in parallel
- consumers must be able to reset the read head to beginning
- consumers must wait if no message is in a certain topic
- producers must be able to write to tail even if someone is reading a message

IDEA:
    We cannot use any arraylist type data structure as the underlying memory will change on reallocation
    We use the linkedlist data structure, only the tail is updated in each add, we make it listenable with a condition
    Every consumer stores a copy of its pointer in the linkedlist, which is immutable* (except for tail)
    The tail may get updated which is then handled with a map and a lock over the map
    The lock/listner is combined into one for convenience

DESIGN:
topic:
    - writeLock
    - writeCond
    - linkedList
    - addMessage(String)
    - String getMessage(linkedListItr) // returns next
    - linkedListItr getHead()
    responsibility:
        - ensure conflict-free addMessage
        - block on getMessage
        - host getHead

consumer:
    - map<topic, linkedListItr>
    - subscribeToTopic(topic)
    - String consumeInTopic(topic)
    - resetToHeadForTopic(topic)
    responsibility:
        - host consumeInTopic, subscribeToTopic and resetToHeadForTopic

producer:
    - produceToTopic(topic, message)
    responsibility:
        host produceToTopic

main:
    - setup topics
    - construct producers and consumers
    - use producer and consumer topics in main to run a random test
