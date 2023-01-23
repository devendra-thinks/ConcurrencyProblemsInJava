package classicproblems;

import java.util.concurrent.Semaphore;

public class DiningPhilosophers {
    Semaphore allowedPhilosopler;
    Semaphore [] sem;

    public DiningPhilosophers() {
        allowedPhilosopler = new Semaphore(4, true);
        sem = new Semaphore[5];
        for(int i = 0; i < 5 ; i++ ) {
            sem[i] = new Semaphore(1, true);
        }
    }

    public void wantsToEat(int philosopher) throws InterruptedException {
        allowedPhilosopler.acquire();
        sem[philosopher].acquire();
        sem[(philosopher + 1)%5].acquire();
        System.out.println("Picking left fork " + philosopher);
        System.out.println("Picking right fork " + (philosopher + 1)%5);
        System.out.println( philosopher + "Started eating  ");
        System.out.println("Putting  left fork " + philosopher);
        System.out.println("putting right fork " + (philosopher + 1)%5);
        sem[philosopher].release();
        sem[(philosopher + 1)%5].release();
        allowedPhilosopler.release();
    }

}
