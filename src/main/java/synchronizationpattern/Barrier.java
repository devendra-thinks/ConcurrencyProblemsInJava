package synchronizationpattern;

import java.util.concurrent.Semaphore;

/**
 * @Author : Devendra
 * This class is an Barrier which stays closed till all n threads arrive and upon
 * arrival it becomes open
 */

public class Barrier {
    static Semaphore mutex;
    static Semaphore turnstile;
    static int workerCount = 0, n;

    static class Worker implements Runnable{

        @Override
        public void run(){
            try{
                mutex.acquire();
                ++workerCount;
                if(workerCount == n)
                    turnstile.release();
                mutex.release();
                System.out.println("Worker " + workerCount + "started waiting on redevzvous!!!!!");
                turnstile.acquire();
                turnstile.release();
                System.out.println("Worker  started working!!!!!");
            }catch (InterruptedException e){

            }finally {

            }
        }
    }

    /**
     * Here we are testing a barrier which will wait for n(10) threads to come
     * and once they are there they start executing
     */
    public static void main(String[] args) throws InterruptedException {
        n = 10;
        mutex = new Semaphore(1, true);
        turnstile = new Semaphore(0 , true);

        for(int i = 0 ; i < 9 ; i++ ){
            new Thread(new Worker()).start();
        }

        System.out.println("Main thread sleeping peacefully!!");
        Thread.sleep(1000);

        new Thread(new Worker()).start();
    }



}
