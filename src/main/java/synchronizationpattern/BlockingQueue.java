package synchronizationpattern;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue {
    private Runnable[] store;
    private Lock lock;
    private Condition available, full;
    private int front = 0 , rear = 0, cnt = 0, SIZE = 10000;

    public BlockingQueue(){
        store = new Runnable[SIZE];
        lock = new ReentrantLock();
        available = lock.newCondition();
        full = lock.newCondition();
    }

    public void add(Runnable element) throws InterruptedException {
        try {
            lock.lock();
            while (cnt == SIZE){
                full.await();
            }
            store[rear++] = element;
            ++cnt;
            if(rear == SIZE ){
                rear = 0;
            }
        }finally {
            available.signal();
            lock.unlock();
        }
    }

    public Runnable poll() throws InterruptedException{
        try{
            lock.lockInterruptibly();
            while(cnt == 0){
                available.await();
            }
            Runnable tmp = store[front++];
            --cnt;
            if(front == SIZE){
                front = 0;
            }
            return tmp;
        }finally {
            full.signal();
            lock.unlock();
        }
    }
}
