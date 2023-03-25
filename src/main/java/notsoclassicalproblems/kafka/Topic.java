package notsoclassicalproblems.kafka;


import java.util.ArrayList;
import java.util.List;

public class Topic{
    List<Message> messages = new ArrayList<>();
    List<Subscriber> subscribers = new ArrayList<>();

    public synchronized void publish(Message message){
        messages.add(message);
        subscribers.stream().forEach(subscriber -> subscriber.wakeUp());
    }

    public synchronized void subscribe(Subscriber subscriber){
            subscribers.add(subscriber);
            new Thread(subscriber).start();
    }
}
