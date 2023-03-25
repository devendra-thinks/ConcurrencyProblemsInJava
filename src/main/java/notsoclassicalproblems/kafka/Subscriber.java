package notsoclassicalproblems.kafka;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Subscriber implements Runnable{
    AtomicInteger offset;
    Topic topic;
    Lock lock;
    Condition condition;
    public Subscriber(Topic topic){
        this.topic = topic;
        offset = new AtomicInteger(0);
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }
    @Override
    public void run() {
        while(true){
            try{
                while(offset.get() >= topic.messages.size()){
                     condition.await();
                }
                Message message = topic.messages.get(offset.get());
                // consumes
                System.out.println(message);
                offset.compareAndSet(offset.get(),offset.get() + 1 );
            }catch (InterruptedException e){
            } catch (Exception e){

            }
        }
    }

    public void resetOffset(int ofc){
        offset.compareAndSet(offset.get(), ofc);
    }

    public void wakeUp(){
        condition.signal();
    }
}
