package notsoclassicalproblems.delayqueue;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementation of schedulers in java which schedules a tak after specific time
 * also configure working threads with the priority blocking queue
 */
public class DelayQueue {
    PriorityBlockingQueue<DelayMessage> pq;
    Lock lock;
    Condition condition;
    DelayQueueWorker[] delayQueueWorkers;
    public DelayQueue(int workers ){
        pq = new PriorityBlockingQueue<>();
        lock  = new ReentrantLock();
        condition = lock.newCondition();
        delayQueueWorkers = new DelayQueueWorker[workers];
        for(int i = 0 ; i < workers ; i++ ){
            delayQueueWorkers[i] = new DelayQueueWorker(this);
            new Thread(delayQueueWorkers[i]).start();
        }
    }

    public void put(DelayMessage delayMessage){
        lock.lock();
        try {
            pq.add(delayMessage);
            if (pq.peek() == delayMessage) {
                condition.signal();
            }
        }finally {
            lock.unlock();
        }
    }
}
