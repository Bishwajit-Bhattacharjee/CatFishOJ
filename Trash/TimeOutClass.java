package Trash;


//Server
import java.util.concurrent.*;

class Task implements Callable {
    @Override
    public Integer call() throws Exception {
        // your code here
        for(Long i = 0L ; i < 10000000000L ; i++) ;

        if(Thread.currentThread().isInterrupted()){
            return Integer.MAX_VALUE;

        }
        return 0;
    }
}

public class TimeOutClass {

    public static void main(String[] args)throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(new Task());
        try {
            System.out.println("Started..");
            System.out.println(future.get(3, TimeUnit.SECONDS) ) ;
            System.out.println("Finished!");
        } catch (TimeoutException e) {
            System.out.println(future.get(3, TimeUnit.SECONDS) ) ;
            future.cancel(true);

            System.out.println("Terminated!");
        }
        executor.shutdownNow();
    }
}