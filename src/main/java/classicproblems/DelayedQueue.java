package classicproblems;

import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DelayedQueue<E extends Delay > implements Queue<E> {
    PriorityQueue<E> pq = new PriorityQueue<>();
    Lock lock = new ReentrantLock();

    Condition available = lock.newCondition();

    Thread leader = null;
    public DelayedQueue(){}

    @Override
    public void put(E e){
        lock.lock();
        try{
            pq.add(e);
            if(pq.peek() == e){
                // If any item can be executed first then execute it
                // by letting available thread to become leader
                leader = null;
                available.signal();
            }
        }finally {
            lock.unlock();
        }
    }

    @Override
    public E get() throws InterruptedException {
        lock.lock();
        try {
            for (;; ) {
                E e = pq.peek();
                if (pq == null) {
                    available.await();
                } else {
                    long delay = e.getDelay(TimeUnit.NANOSECONDS);
                    if (delay <= 0) {
                        return pq.poll();
                    } else {
                        if (leader != null) {
                            available.await();
                        } else {
                            e = null; // make it eligible for garbage collection
                            Thread curr  = Thread.currentThread();
                            leader = curr;
                            try {
                                available.awaitNanos(delay);
                            } finally {
                                // for doing timed waiting
                                if(leader == curr) {
                                    leader = null;
                                }
                            }
                        }
                    }
                }
            }
        }finally {
            if(leader == null && !pq.isEmpty()){
                available.signal();
            }
            lock.unlock();
        }
    }

    @Override
    public boolean isEmpty(){
        lock.lock();
        try{
            return pq.isEmpty();
        }finally {
            lock.unlock();
        }
    }

}
