package threadpools;

import java.util.concurrent.BlockingQueue;

public class FixedThreadPoolRunnable implements Runnable{

    private boolean isStop = false;
    private BlockingQueue<Runnable > queue;
    private Thread thread;
    public FixedThreadPoolRunnable(BlockingQueue<Runnable > queue){
        this.queue = queue;
    }

    @Override
    public void run(){
        this.thread = Thread.currentThread();
        while(!isStop){
            System.out.println("worker " + Thread.currentThread().getName() + " started executing... ");
            Runnable runnable = null;
            try {
                runnable = queue.take();
            } catch (InterruptedException e) {

            }
            if(runnable != null )
                  runnable.run();
        }
        System.out.println("worker " + Thread.currentThread().getName() + " stopped... ");
    }

    public void stop(){
        isStop = true;
        this.thread.interrupt();
    }
}
