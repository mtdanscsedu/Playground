
import java.io.*;
import java.util.*;



/**
 *
 * @author Ashif
 */



public class test {
    
    public static void main(String[] args) throws IOException {
        System.out.println("JJK");
        
        ClientSynchronizer cs=new ClientSynchronizer("d:/temp/client_2", 4444);
        cs.start();
    }
}
