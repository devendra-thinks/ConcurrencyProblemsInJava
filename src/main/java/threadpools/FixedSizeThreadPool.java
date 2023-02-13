package threadpools;

import classicproblems.BlockingQueue;

import java.util.ArrayList;
import java.util.List;

public class FixedSizeThreadPool {
    BlockingQueue<Runnable > queue;
    List<FixedThreadPoolRunnable> workers;
    private boolean isStop = false;
    int n;

    public FixedSizeThreadPool(int n){
        this.n = n;
        queue = new classicproblems.BlockingQueue(100);
        workers = new ArrayList<>();

        for(int i = 0 ; i < n ; ++i){
            workers.add(new FixedThreadPoolRunnable(queue));
            new Thread(workers.get(i)).start();
        }
    }

    synchronized public void submit(Runnable runnable) throws IllegalAccessException, InterruptedException {
        if(isStop){
            throw  new IllegalAccessException("Executor service stopping...");
        }
        queue.put(runnable);
    }

    synchronized public void stop(){
        isStop = true;
        for(int i = 0 ; i < n ; i++ ){
            workers.get(i).stop();
        }
    }

    synchronized public void awaitTermination() throws InterruptedException {
        while (queue.isEmpty()){
            Thread.sleep(10);
        }
    }
}
