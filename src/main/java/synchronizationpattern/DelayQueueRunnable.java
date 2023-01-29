package synchronizationpattern;

public class DelayQueueRunnable {
    Runnable runnable;
    long  startTime;

    public DelayQueueRunnable(Runnable runnable, long startTime){
        this.runnable = runnable;
        this.startTime = startTime;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public long getStartTime() {
        return startTime;
    }
}
