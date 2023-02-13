package threadpools;

import classicproblems.DelayRunnable;
import classicproblems.DelayedQueue;

public class FixedThreadPoolWithDelay {
    DelayedQueue<DelayRunnable> queue;
    FixedThreadPoolRunnable [] workers;
    boolean isStop = false;
    int n ;

    public FixedThreadPoolWithDelay(int coreThreadPool){
        queue = new DelayedQueue<>();
        n = coreThreadPool;
        workers = new FixedThreadPoolRunnable[coreThreadPool];
        for(int i = 0 ; i < coreThreadPool; ++i  ){
            workers[i] = new FixedThreadPoolRunnable(queue);
            new Thread(workers[i]).start();
        }
    }

    synchronized public void submit(DelayRunnable runnable) throws IllegalAccessException {
        if(isStop){
            throw  new IllegalAccessException("Executor service stopping...");
        }
        queue.put(runnable);
    }

    synchronized public void stop(){
        isStop = true;
        for(int i = 0 ; i < n ; i++ ){
            workers[i].stop();
        }
    }

    synchronized public void awaitTermination() throws InterruptedException {
        while (queue.isEmpty()){
            Thread.sleep(10);
        }
    }
}
