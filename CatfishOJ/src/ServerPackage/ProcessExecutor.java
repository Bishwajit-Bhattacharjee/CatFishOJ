package ServerPackage;
import java.io.*;

//Server
class StreamGobbler implements Runnable {
    InputStream is;
    String type;
    OutputStream os;
    Thread t;
    String str;
    
    StreamGobbler(InputStream is, String type) {
    	this.is = is;
        this.type = type;
        this.os = null;
    	t=new Thread(this);
    } 

    StreamGobbler(InputStream is, String type, OutputStream redirect) {
        this.is = is;
        this.type = type;
        this.os = redirect;
        t=new Thread(this);
    }
    
    StreamGobbler(OutputStream os,String type, InputStream redirect) {
        this.os = os;
        this.type = type;
        this.is = redirect;
        t=new Thread(this);
    }
    
    public void start() {
    	t.start();
    }

    public void run() {
        try {
            PrintWriter pw = null;
            if (os != null) pw = new PrintWriter(os);                
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ( (line = br.readLine()) != null)
            {
                if (pw != null) pw.println(line);
                //System.out.println(type + ">" + line);
            }
            if (pw != null) pw.flush();
        } catch (Exception ioe) {
            ioe.printStackTrace();  
        }
    }
}

public class ProcessExecutor {

    public String inputString;
    public String outputString;
    public String errorString;

    private int exitVal;

    public int getExitVal() {
        return exitVal;
    }

    public ProcessExecutor(String processName, String input, String output) {
        try {
            FileOutputStream fos = new FileOutputStream(output);
            FileInputStream fis = new FileInputStream(input);
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(processName);
            //System.out.println(processName);
            // any error message?
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "Error");
            // any input
            StreamGobbler inputGobbler = new StreamGobbler(proc.getOutputStream(),"Input", fis);
            // any output
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "Output", fos);

            // kick them off
            errorGobbler.start();
            inputGobbler.start();
            outputGobbler.start();

            // any error???
            exitVal = proc.waitFor();

            inputString = inputGobbler.str;
            outputString = outputGobbler.str;
            errorString = errorGobbler.str;


//            System.out.println( outputString );
//            System.out.println( errorString );
//            System.out.println( inputString );
            fis.close();
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
