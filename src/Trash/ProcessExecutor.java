package Trash;
public class ProcessExecutor {
    
    public static void main(String args[]) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("gcc  test.c");
            int exitValue = proc.waitFor();
            System.out.println(exitValue);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
