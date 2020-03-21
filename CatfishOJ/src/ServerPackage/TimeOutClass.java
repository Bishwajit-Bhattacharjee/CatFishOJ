package ServerPackage;

import Common.Problem;

import java.util.concurrent.*;
//Server
class Task implements Callable {
    private Problem problem;
    public Task(Problem problem) {
        this.problem = problem;
    }

    @Override
    public Integer call() throws Exception {
        new ProcessExecutor("a.exe", problem.getINPUT_FILE_NAME(), "out.txt");
        return 0;
    }
}

public class TimeOutClass {
    private Problem problem;
    private int returnVal;

    public TimeOutClass(Problem problem) {
        this.problem = problem;
    }

    public int check() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(new Task(problem));
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