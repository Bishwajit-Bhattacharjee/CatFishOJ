package Common;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
//Server + Client
public class Problem implements Serializable{
    public static ArrayList<Problem> problemList = new ArrayList<>() ;


    private String ProblemID, ProblemName;
    private int distinctUserSolved,// How many user
            distinctUserTriedCnt, acceptedCnt , totalCnt ;

    private  String INPUT_FILE_NAME ;
    private  String OUTPUT_FILE_NAME ;

    public Problem(String problemID, String problemName) {
        this.ProblemID = problemID;
        this.ProblemName = problemName;
        INPUT_FILE_NAME = problemID + "in.txt";
        OUTPUT_FILE_NAME = problemID + "out.txt";
        this.distinctUserSolved = 0;
        this.distinctUserTriedCnt = 0;
        this.acceptedCnt =  0;
        this.totalCnt = 0 ;

    }

    public int getDistinctUserSolved() {
        return distinctUserSolved;
    }

    public void setDistinctUserSolved(int distinctUserSolved) {
        this.distinctUserSolved = distinctUserSolved;
    }

    public int getDistinctUserTriedCnt() {
        return distinctUserTriedCnt;
    }

    public void setDistinctUserTriedCnt(int distinctUserTriedCnt) {
        this.distinctUserTriedCnt = distinctUserTriedCnt;
    }

    public int getAcceptedCnt() {
        return acceptedCnt;
    }

    public void setAcceptedCnt(int acceptedCnt) {
        this.acceptedCnt = acceptedCnt;
    }

    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }

    public String getINPUT_FILE_NAME() {
        return INPUT_FILE_NAME;
    }

    public String getOUTPUT_FILE_NAME() {
        return OUTPUT_FILE_NAME;
    }

    public static ArrayList<Problem> getProblemList() {
        return problemList;
    }
    public static ObservableList<Problem> getProblemListObservable() {
        ObservableList<Problem> list = FXCollections.observableArrayList(problemList);
        return list;
    }


    public StringProperty getProblemIDProperty() {
        return new SimpleStringProperty(ProblemID);
    }

    public StringProperty getProblemNameProperty() {
        return new SimpleStringProperty(ProblemName);
    }



    @Override
    public String toString() {
        return
                ProblemID + "," +
                ProblemName +
                ","+ distinctUserSolved + "," +
                        distinctUserTriedCnt + "," +
                 acceptedCnt + "," + totalCnt ;

    }



    public String getProblemID() {
        return ProblemID;
    }

    public String getProblemName() {
        return ProblemName;
    }

    public Problem() {

        ProblemID = "";
        ProblemName = "";
        distinctUserTriedCnt = 0 ;

        acceptedCnt = 0 ;
        totalCnt =  0 ;
    }

    public static void init() {

        problemList.add( new Problem("1001" , "Hello Catfish") );
        problemList.add( new Problem("1002" , "Catfish") );
        problemList.add( new Problem("1003" , "Hello") );
        problemList.add( new Problem("1004" , "Zawad and His Poor Brain") );

       // System.out.println("Problem Count:" + problemList.size());
    }
}
