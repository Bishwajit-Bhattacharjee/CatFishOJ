package Trash;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        Runtime rt= Runtime.getRuntime();
        try {
            Process p1 = Runtime.getRuntime().exec("gcc test.c" );
            int va = p1.waitFor();
            System.out.println(va);
            Process p2 = Runtime.getRuntime().exec("a.exe");
            int vb = p2.waitFor();
            System.out.println(vb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

