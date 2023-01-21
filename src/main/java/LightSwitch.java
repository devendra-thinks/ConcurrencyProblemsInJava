import java.util.concurrent.Semaphore;

/**
 *  This class is an implementation of LightSwitch pattern
 */
public class LightSwitch {
    int count ;
    Semaphore mutex;

    public LightSwitch(){
        count = 0;
        mutex = new Semaphore(1, true);
    }

    /**
     * This method is used to take lock on param based on lightswitch pattern
     * @param semaphore
     * @throws InterruptedException
     */
    public void lock(Semaphore semaphore) throws InterruptedException{
        mutex.acquire();
        try {
            ++count;
            if (count == 1) {
                semaphore.acquire();
            }
        }finally {
            mutex.release();
        }
    }

    /**
     * This method is used to release lock on param based on lightswitch pattern
     * @param semaphore
     * @throws InterruptedException
     */
    public void unlock(Semaphore semaphore) throws InterruptedException{
        mutex.acquire();
        try {
            --count;
            if (count == 0) {
                semaphore.release();
            }
        }finally {
            mutex.release();
        }
    }
}
