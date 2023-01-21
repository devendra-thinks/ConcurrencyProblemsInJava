package synchronizationpattern.signal;

import java.util.logging.Logger;

public class Decrementor  implements Runnable{
    Signal signal;
    Logger logger;


    public Decrementor( Signal signal){
        this.signal = signal;
        logger = Logger.getLogger("Decrementor");
    }


    @Override
    public void run() {
        try {
            if(signal.count  == 0 ) {
                signal.semaphore.acquire();
            }
            --signal.count;
            logger.info( " decremented current count is " + signal.count);
        }catch (InterruptedException e){
        }
    }
}
