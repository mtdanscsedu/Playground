
package com.ashif.net;


import java.io.*;
import java.net.*;


/**
 *
 * @author Ashif
 */
public class Client {
    
    public static void main(String[] args) throws IOException {
    
        Socket sock=new Socket("localhost",3500);
        
        System.out.println("Client Window");
        
        new Thread(new Sender(sock, "client")).start();
        new Thread(new Receiver(sock)).start();
    }
    
}
