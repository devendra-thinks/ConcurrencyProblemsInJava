package threadpools;

import classicproblems.Queue;


public class FixedThreadPoolRunnable implements Runnable{

    private boolean isStop = false;
    private Queue<? > queue;
    private Thread thread;
    public FixedThreadPoolRunnable(Queue<? > queue){
        this.queue = queue;
    }

    @Override
    public void run(){
        this.thread = Thread.currentThread();
        while(!isStop){
            Runnable runnable = null;
            try {
                runnable = (Runnable) queue.get();
            } catch (InterruptedException e) {

            }
            if(runnable != null )
                  runnable.run();
        }
    }

    public void stop(){
        isStop = true;
        this.thread.interrupt();
    }
}
