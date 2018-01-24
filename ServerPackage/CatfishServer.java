package ServerPackage;

//Server

import Common.NetworkUtil;
import Common.Problem;
import Common.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class CatfishServer {

    final static int NUMBER_OF_PROBLEMS = 4 ;


    private ServerSocket serverSocket;
    public HashMap<Integer, NetworkUtil> clientMap;
    public  static int clientCount = 0;

    public static ArrayList<User> userRecords = new ArrayList<>() ;
  //  public ArrayList<Problem> problemList = new ArrayList<>();
    private static final String USER_FILE_NAME = "UserRecord.txt";
    private static final String PROBLEM_FILE_NAME = "ProblemRecord.txt" ;



//    public static ArrayList<User> getUserRecords()
//    {
//
//        return userRecords;
//    }
//
//    public static void setUserRecords(ArrayList<User> userRecords) {
//        CatfishServer.userRecords = userRecords;
//    }

    CatfishServer() {
        clientMap = new HashMap<>() ;

        try {
            serverSocket = new ServerSocket(3333);

            while(true) {

                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally{
            System.out.println("finally");
            writeFile();
        }
    }

    static void loadFile() {

        try {

            String line;
            String[] lineVector1;
            BufferedReader br = new BufferedReader(new FileReader(USER_FILE_NAME) );

            while( (line = br.readLine() ) !=null) {

                lineVector1 = line.split("/");
                getInfo(lineVector1) ;
            }
        } catch( Exception  e ) {

            e.printStackTrace();
        }


        String line;
        Problem.init();
        String[] lineVector1;
        try{
            BufferedReader br = new BufferedReader(new FileReader(PROBLEM_FILE_NAME) );
            int cnt = 0;
            while( (line = br.readLine() ) !=null) {

                lineVector1 = line.split(",");
                Problem tmp =Problem.problemList.get(cnt);
                tmp.setDistinctUserSolved(Integer.parseInt(lineVector1[2]) );
                tmp.setDistinctUserTriedCnt(Integer.parseInt(lineVector1[3]));
                tmp.setAcceptedCnt(Integer.parseInt(lineVector1[4]));
                tmp.setTotalCnt(Integer.parseInt(lineVector1[5]));
                Problem.problemList.set(cnt ,tmp) ;
                cnt++;
            }

        }catch (Exception e ) {
            e.printStackTrace();
        }




    }

    private static void getInfo(String[] InfoTaken ) {
        if(InfoTaken != null) {
            String Info[] = InfoTaken[0].split(",") ;
            String secInfo[] = InfoTaken[1].split(",") ;
            HashMap < String , Integer > map = new HashMap<>() ;
            for(int i = 0; i < NUMBER_OF_PROBLEMS ; i++){
                map.put(secInfo[2*i], Integer.parseInt(secInfo[2*i + 1]) ) ;
            }
            userRecords.add( new User(Info[0] , Info[1] , Integer.parseInt(Info[2]), map ,  Integer.parseInt(Info[3]), Integer.parseInt(Info[4]) ,Integer.parseInt(Info[5]),
                    Integer.parseInt(Info[6])) ) ;
        }


    }
    public static void addUser(User user) {
        userRecords.add(user);
    }

    public static void writeFile() {

        ArrayList<Problem> list = new ArrayList<>(Common.Problem.problemList);
        System.out.println("FROM WRITER" + list.size());
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE_NAME)) ;

            for(User user: userRecords){

                String str = user.toString() ;
                bw.write(str);
                bw.write("\n");
            }

            bw.close() ;

        }catch (Exception e){
            e.printStackTrace();
        }


        try(BufferedWriter bw = new BufferedWriter(new FileWriter(PROBLEM_FILE_NAME))) {
            System.out.println(Problem.problemList.size());
            for(Problem problem: Problem.problemList) {
                String str = problem.toString() ;
                bw.write(str) ;
                bw.write("\n");
            }

            bw.close();

        }catch (Exception e ) {
            e.printStackTrace();
        }


    }

    /**
     *  public String toString() {
     System.out.println(CompileExitVal +","+  ExecuteExitVal + "," + CPUTime + "," + verdictInt);
     return CompileExitVal +","+  ExecuteExitVal + "," + CPUTime + "," + verdictInt ;
     }

     * @param clientSocket
     */


    public void serve(Socket clientSocket) {

        NetworkUtil nc = new NetworkUtil(clientSocket);

        clientMap.put(++clientCount , nc);
        nc.write(clientCount);
        System.out.println("hello from serve " + clientCount);
        new ServerReadThread(nc, clientCount , clientMap);
    }

    public static void updateUserInfoFromVerdict(String verdict, User currentUser, Problem currentProblem)
    {
        String[] fullInfo = verdict.split(",") ;

        int isSolved = -1 ;

        currentProblem.setTotalCnt(currentProblem.getTotalCnt() + 1 );

        int exitVal = Integer.parseInt(fullInfo[0]);
        double cpuTime = 0;
        String str;
        if( exitVal != 0){
            str = "Compilation Error!!";
            int tmp = currentUser.getTotalCE() ;
            currentUser.setTotalCE(tmp + 1 ) ;
        }
        else {

            exitVal = Integer.parseInt(fullInfo[1]);
            if( exitVal != 0) {
                str = "TLE!!";

                int tmp =  currentUser.getTotalTLE() ;
                currentUser.setTotalTLE(tmp + 1 ) ;
                System.out.println(currentUser.getTotalTLE() )  ;

            }
            else if( Integer.parseInt(fullInfo[3]) == 1) {
                str = "Wrong Answer!!";

                int tmp = currentUser.getTotalWA() ;
                currentUser.setTotalWA(tmp + 1 ) ;
                System.out.println( currentUser.getTotalWA() )  ;

            }
            else{
                str = "AC!!";
                isSolved = 1;

                currentProblem.setAcceptedCnt(currentProblem.getAcceptedCnt() + 1 );


                if(currentUser.getIsSolvedMap()
                        .get(currentProblem
                        .getProblemID()) != 1){
                    currentProblem.setDistinctUserSolved(currentProblem.getDistinctUserSolved() + 1);
                }
                int tmp = currentUser.getTotalAC() ;
                if(currentUser.getIsSolvedMap()
                        .get(currentProblem
                                .getProblemID()) != 1){

                    currentUser.setTotalSolved(currentUser
                            .getTotalSolved() + 1 ) ;
                }
                currentUser.setTotalAC(tmp + 1 ) ;
                System.out.println(currentUser.getTotalAC() )  ;

            }

        }
        System.out.println("hellllllllllllo from catSer Map " + currentUser.getIsSolvedMap());
        if(currentUser.getIsSolvedMap().get(currentProblem.getProblemID()) == 0 && isSolved == -1 ){
            currentProblem.setDistinctUserTriedCnt(currentProblem.getDistinctUserTriedCnt() + 1 );
        }
        if(currentUser.getIsSolvedMap().get(currentProblem.getProblemID()) != 1 )
            currentUser.getIsSolvedMap().put(currentProblem.getProblemID(),isSolved );

        for(int i =0 ; i < userRecords.size(); i++)
        {
            if(userRecords.get(i).getHandle().equalsIgnoreCase(currentUser.getHandle())){
                userRecords.set(i, currentUser) ;
            }
        }

       // Problem.init();

        for(int i =0 ; i < Problem.problemList.size(); i++) {
            System.out.println("Update from problemList"+ Problem.problemList.size() );
            if( Problem.problemList.get(i).getProblemID().equals(currentProblem.getProblemID())){
                Problem.problemList.set(i, currentProblem) ;
            }
        }

        writeFile();

    }

    public static void main(String[] args) {
        loadFile();
        new CatfishServer();
        System.out.println("server");
       // writeFile();
    }
}
