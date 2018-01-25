package ServerPackage;

import Common.ClientSubmit;
import Common.Problem;
import Common.User;

import java.io.*;

//Server
public class Verdict {

    private Problem problem;
    private User currentUser;
    private ClientSubmit clientSubmit;
    private int CompileExitVal = Integer.MAX_VALUE;
    private int ExecuteExitVal = Integer.MAX_VALUE;
    private String submitLanguage;
    private double CPUTime = 0.0;
    private int verdictInt = -1;
    private static final String SUBMITTED_FILE_NAME_C = "userSolution.c";
    private static final String SUBMITTED_FILE_NAME_CPP = "userSolution.cpp";
    private static final String SUBMITTED_FILE_NAME_JAVA = "userSolution.java";


    public  Verdict(ClientSubmit clientSubmit) {
        this.problem = clientSubmit.getCurrentProblem() ;
        this.currentUser = clientSubmit.getCurrentUser() ;
        this.submitLanguage = clientSubmit.getSubmitLanguage();
        System.out.println("hello from verdict + " +submitLanguage);

        try {
            BufferedWriter bw = null;
            if(submitLanguage.equals("C"))
                bw = new BufferedWriter(new FileWriter(SUBMITTED_FILE_NAME_C));
            else if( submitLanguage.equals("C++"))
                bw = new BufferedWriter(new FileWriter(SUBMITTED_FILE_NAME_CPP));
            System.out.println(clientSubmit.getSolution());
            bw.write(clientSubmit.getSolution());
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.clientSubmit = clientSubmit;
        problem = clientSubmit.getCurrentProblem();
        currentUser = clientSubmit.getCurrentUser();
        CompileExitVal= compile();
        if( CompileExitVal == 0 ) {
            ExecuteExitVal = execute();

            if(ExecuteExitVal == 0) {

                verdictInt = isWrongAns();
            }
            else  {
                CPUTime = 3.00;
                verdictInt = 2;
            }
        }
    }

    @Override
    public String toString() {
        System.out.println(CompileExitVal +","+  ExecuteExitVal + "," + CPUTime + "," + verdictInt + "," + submitLanguage);
        return CompileExitVal +","+  ExecuteExitVal + "," + CPUTime + "," + verdictInt + "," + submitLanguage;
    }

    public Verdict(Problem problem, User currentUser) {
        this.problem = problem;
        this.currentUser = currentUser;

    }

    public int compile() {

        File file = new File(currentUser.getHandle() + ".exe");
        if( file.exists() ) file.delete();


        if(clientSubmit.getSubmitLanguage().equals( "C" )){
            new ProcessExecutor("gcc userSolution.c -o " + currentUser.getHandle() + ".exe", "in.txt", "out.txt");

            File file1 = new File(currentUser.getHandle() + ".exe");
            if( file1.exists() ) return 0;
            return 1234;

        }
        else if(clientSubmit.getSubmitLanguage().equals("C++") ) {
            String cmd = "g++ userSolution.cpp -o " + currentUser.getHandle() + ".exe";
            System.out.println(cmd);
            new ProcessExecutor(cmd, "in.txt", "out.txt");

            File file1 = new File(currentUser.getHandle() + ".exe");
            if( file1.exists() ) return 0;
            return 1234;
        }
        else if( clientSubmit.getSubmitLanguage().equals("Java")){
            // write  for Java -_-
            //new ProcessExecutor("javac userSolution.java", "in.txt","out.txt");
            return 1234;
        }

        return 1234 ;
    }

    public int execute() {

        long startTime = System.currentTimeMillis();
        int getVal = 0;
        try {
            getVal = new TimeOutClass(problem , clientSubmit).check();
        } catch (Exception e) {
            e.printStackTrace();
        }

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        CPUTime = (totalTime/1000.0);
        return getVal ;
        //  return executorProcess.getExitVal();00.0);
    }

    public int isWrongAns() {

        boolean areEqual = true;
        int line = 1;

        try {
//            System.out.println(problem.getOUTPUT_FILE_NAME());
            BufferedReader solutionReader = new BufferedReader(new FileReader(problem.getOUTPUT_FILE_NAME() ) );

            BufferedReader userReader = new BufferedReader(new FileReader("out.txt"));

            String line1, line2 ;

            int cnt =0 ;
            do{
                line1 = solutionReader.readLine();

                line2 = userReader.readLine();
                if(line1 == null && line2 == null ) {
                    break;
                }
                if(line2 == null ) {

                    if(line1 != null){

                        areEqual = false;
//                        System.out.println(cnt + "1");
                        break;
                    }
                }
                else if(line1 == null) {

                    if(line2 != null) {
                        areEqual = false;
//                        System.out.println(cnt + "2");
                        break;
                    }
                }

                line1 = line1.trim() ;
                line2 = line2.trim() ;
                if( ! line1.equals(line2) ){
                    areEqual = false ;
//                    System.out.println(cnt + "3");
                    break;
                }
//                System.out.println(line1);
//                System.out.println(line2);
                cnt++;


            }while(line1 != null || line2 != null );


            userReader.close();

            solutionReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(! areEqual) return 1; // 1 for WA
        return 0;
    }

}
