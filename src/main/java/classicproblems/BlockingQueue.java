package classicproblems;


/**
 * Solves bounded buffer problems by using semaphores only
 */
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<T >{
    int size, front , rear, count;
    Object  [] items;
    Lock lock;
    Condition isFull , isEmpty;

    public BlockingQueue(int size){
        items = new Object[size];
        this.size = size;
        front = rear = count = 0;
        lock = new ReentrantLock();
        isFull = lock.newCondition();
        isEmpty = lock.newCondition();
    }

    public void put(T e) throws InterruptedException {
        lock.lock();
        try{
            while(count == size)
                isFull.await();
            items[rear] = e;
            rear = (rear + 1)%size;
            ++count;
            isEmpty.signal();
        }finally {
            lock.unlock();
        }
    }


    public T get() throws InterruptedException {
        lock.lock();
        try{
            while(count == 0)
                isEmpty.await();
            T ele =  (T) items[front];

            front = (front + 1)%size;
            --count;
            isFull.signal();
            return ele;
        }finally {
            lock.unlock();
        }
    }

    public boolean isEmpty(){
        lock.lock();
        try{
            return count == 0;
        }finally {
            lock.unlock();
        }
    }
}