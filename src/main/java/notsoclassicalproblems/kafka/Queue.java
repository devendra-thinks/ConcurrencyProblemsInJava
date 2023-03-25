package notsoclassicalproblems.kafka;

/**
 *  Publisher subscriber Queue - facilitates multiple scubscribers to consume
 *  from topics keep track of their own offset
 *  and publishers to publish message without blocking to a topic
 *
 */

import java.util.Map;

public class Queue {
    private  Map<String , Topic> topics;

    public void createTopic(String name, Topic topic ){
        topics.put(name, topic);
    }
}
