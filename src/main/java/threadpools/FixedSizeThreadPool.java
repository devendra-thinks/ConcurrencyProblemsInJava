package threadpools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FixedSizeThreadPool {
    BlockingQueue<Runnable > queue;
    List<FixedThreadPoolRunnable> workers;
    private boolean isStop = false;
    int n;

    public FixedSizeThreadPool(int n){
        this.n = n;
        queue = new LinkedBlockingQueue<>();
        workers = new ArrayList<>();

        for(int i = 0 ; i < n ; ++i){
            workers.add(new FixedThreadPoolRunnable(queue));
            new Thread(workers.get(i)).start();
        }
    }

    synchronized public void submit(Runnable runnable) throws IllegalAccessException {
        if(isStop){
            throw  new IllegalAccessException("Executor service stopping...");
        }
        queue.add(runnable);
    }

    synchronized public void stop() throws InterruptedException {
        awaitTermination();
        isStop = true;
        for(int i = 0 ; i < n ; i++ ){
            workers.get(i).stop();
        }
    }

    synchronized public void awaitTermination() throws InterruptedException {
        while (!queue.isEmpty()){
            Thread.sleep(1000);
        }
    }
}
