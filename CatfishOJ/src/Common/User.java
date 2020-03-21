package Common;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Server + Client
public class User implements Serializable{

    final int NUMBER_OF_PROBLEMS = 3 ;

    private  String handle;
    private  String password;
    private int totalSolved;
    private int totatSubmissions ; // it is the sum of all verdicts
    private int totalAttempted;

    private HashMap<String , Integer > isSolvedMap ; // String -> ID , Integer -> type of status ( -1 -> unsolved , 1 -> solved , 0 -> untouched )


    private int totalAC = 0;
    private int totalWA = 0;
    private int totalTLE = 0;

    public HashMap<String, Integer> getIsSolvedMap() {
        return isSolvedMap;
    }

    public void setIsSolvedMap(HashMap<String, Integer> isSolvedMap) {
        this.isSolvedMap = isSolvedMap;
    }

    private int totalCE = 0;

    public int getTotalCE() {
        return totalCE;
    }

    public void setTotalCE(int totalCE) {
        this.totalCE = totalCE;
    }

    public int getTotalAC() {
        return totalAC;
    }

    public void setTotalAC(int totalAC) {
        this.totalAC = totalAC;
    }

    public int getTotalWA() {
        return totalWA;
    }

    public void setTotalWA(int totalWA) {
        this.totalWA = totalWA;
    }

    public int getTotalTLE() {
        return totalTLE;
    }

    public void setTotalTLE(int totalTLE) {
        this.totalTLE = totalTLE;
    }

    public User(String handle, String password, int totalSolved, int totalAC, int totalWA, int totalTLE, int totalCE) {
        this.handle = handle;
        this.password = password;
        this.totalSolved = totalSolved;
        this.totalAC = totalAC;
        this.totalWA = totalWA;
        this.totalTLE = totalTLE;
        this.totalCE = totalCE;
        this.totatSubmissions = totalAC + totalCE + totalTLE + totalWA ;
        this.totalAttempted = 0;

        isSolvedMap = new HashMap<>() ;
        for(int i = 1; i <= NUMBER_OF_PROBLEMS; i++)
        {
            isSolvedMap.put(Integer.toString(1000 + i ) , 0 ) ;
//)
        }

    }




    public String getHandle() {
        return handle;
    }

    public String getPassword() {
        return password;
    }

    public int getTotalSolved() {
        return totalSolved;
    }

    public void setTotalSolved(int totalSolved) {
        this.totalSolved = totalSolved;
    }

    public int getTotalAttempted() {
        return totalAttempted;
    }

    public User(String handle, String password, int totalSolved, HashMap<String, Integer> isSolvedMap, int totalAC, int totalWA, int totalTLE, int totalCE) {
        this.handle = handle;
        this.password = password;
        this.totalSolved = totalSolved;
        this.isSolvedMap = isSolvedMap;
        this.totalAC = totalAC;
        this.totalWA = totalWA;
        this.totalTLE = totalTLE;
        this.totalCE = totalCE;

        this.totatSubmissions = totalAC + totalCE + totalTLE + totalWA ;

    }

    @Override
    public String toString() {

        String tmp =  handle + "," + password  +
                "," + totalSolved +
                "," + totalAC +
                "," + totalWA +
                "," + totalTLE +
                "," + totalCE +
                "," + totalAttempted
                ;
        tmp += "/" ;

        for(Map.Entry<String ,Integer> entry : isSolvedMap.entrySet()){
            tmp += entry.getKey() + "," + Integer.toString(entry.getValue() ) + "," ;
        }
        return tmp ;


    }
}