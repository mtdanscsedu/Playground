
package com.ashif.net;


import java.io.*;
import java.net.*;


/**
 *
 * @author Ashif
 */
public class Server {
    
    public static void main(String[] args) throws IOException {
        
        ServerSocket server=new ServerSocket(3500);
        System.out.println("Server Window");
        Socket sock=server.accept();               
        
        new Thread(new Sender(sock, "Server")).start();
        new Thread(new Receiver(sock)).start();
        
    }
}
