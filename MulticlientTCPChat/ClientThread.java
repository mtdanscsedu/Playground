
package com.ashif.net;


import java.net.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author Ashif
 */



public class ClientThread extends Thread{
    
    public Socket client_socket;
    private DataInputStream input;
    private DataOutputStream output;
    protected String User_Name;
    public static ArrayList<ClientThread> Client_list;

    
    
    public ClientThread(Socket client_Socket, String User_Name, ArrayList<ClientThread> Client_list) throws IOException{
        
        this.client_socket= client_Socket;
        this.Client_list= Client_list;
        
        this.User_Name= User_Name;
        
        input= new DataInputStream(client_socket.getInputStream());
        output= new DataOutputStream(client_socket.getOutputStream());
    }
    
    
    @Override
    public void run(){
        
        while (true) {            
            try {
                
                String str=input.readUTF().trim();
                Broadcast(str,User_Name);
                
            } catch (Exception e) {
            }
        }
        
    }  
    
    
    
    public boolean SendToClient(String msg){
        try {
            
            output.writeUTF(msg);
            return true;
            
        } catch (Exception e) {
            System.out.println("Exception inside ClientThread SendToClient(String msg) "+e);
            return false;
        }
        
    }

    
    
    public synchronized static void Broadcast(String msg, String User_id){
        
        int i;
        
        for(i=0; i<Client_list.size(); i++){
            
            ClientThread ct= Client_list.get(i);
            
            if(ct.User_Name.equalsIgnoreCase(User_id))
                continue;
            
            if(!ct.SendToClient(msg))
                remover(ct.User_Name);
        }
    }  
    
    
    
    public static void remover(String User_id){
        int i;
        
        for(i=0; i<Client_list.size(); i++){
            
            ClientThread ct= Client_list.get(i);
            
            if(ct.User_Name==User_id){
                Client_list.remove(i);
                return;
            }    
        }
    }
}
