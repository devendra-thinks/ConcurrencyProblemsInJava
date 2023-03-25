package notsoclassicalproblems.delayqueue;

import notsoclassicalproblems.delayqueue.DelayMessage;
import notsoclassicalproblems.delayqueue.DelayQueue;

import java.util.concurrent.TimeUnit;

public class DelayQueueWorker implements Runnable{

    DelayQueue delayQueue;

    public DelayQueueWorker(DelayQueue delayQueue){
        this.delayQueue = delayQueue;
    }

    @Override
    public void run() {
        while(true){
            delayQueue.lock.lock();
            try {
                DelayMessage delayMessage = delayQueue.pq.peek();
                if(delayMessage != null ) {
                    if( delayMessage.time >= System.currentTimeMillis()) {
                        long sleepTime = delayMessage.time - System.currentTimeMillis();
                        delayQueue.condition.await(sleepTime , TimeUnit.MILLISECONDS);
                    }
                    else{
                        delayMessage = delayQueue.pq.poll();
                        System.out.println("Execution started by " + Thread.currentThread().getName() + " delay  " +  delayMessage.name );
                        delayMessage.runnable.run();
                    }
                }else{
                    delayQueue.condition.await();
                }
            }catch (InterruptedException e){
                // digest
            }finally {
                delayQueue.lock.unlock();
            }
        }
    }
}
