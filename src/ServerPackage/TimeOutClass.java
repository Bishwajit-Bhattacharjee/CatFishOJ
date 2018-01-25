package ServerPackage;

import Common.ClientSubmit;
import Common.Problem;

import java.util.concurrent.*;
//Server
class Task implements Callable {
    private Problem problem;
    private ClientSubmit clientSubmit;
    public Task(Problem problem,ClientSubmit clientSubmit) {
        this.problem = problem;
        this.clientSubmit= clientSubmit;
    }

    @Override
    public Integer call() throws Exception {
        if(clientSubmit.getSubmitLanguage().equals("Java"))new ProcessExecutor("java -jar userSolution.jar", problem.getINPUT_FILE_NAME(), "out.txt");
        else new ProcessExecutor(clientSubmit.getCurrentUser().getHandle() + ".exe", problem.getINPUT_FILE_NAME(), "out.txt");
        if(Thread.currentThread().isInterrupted()){
            return 1234567;
        }

        return 0;
    }
}

public class TimeOutClass {
    private Problem problem;
    private int returnVal;
    private ClientSubmit clientSubmit;
    public TimeOutClass(Problem problem, ClientSubmit clientSubmit) {
        this.problem = problem;
        this.clientSubmit = clientSubmit;
    }

    public int check() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(new Task(problem, clientSubmit));
        try {
            System.out.println("Started..");
            future.get(3, TimeUnit.SECONDS);
            System.out.println("Finished!");
            returnVal = 0;
        } catch (TimeoutException e) {
            future.cancel(true);
            System.out.println("Terminated!");
            returnVal = 1;
        }
        executor.shutdownNow();
        return returnVal;
    }
}