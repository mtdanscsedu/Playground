

import java.io.*;
import java.net.*;
import static java.lang.System.currentTimeMillis;
//import java.nio.file.*;
//import java.nio.file.attribute.*;


/**
 *
 * @author Ashif
 */
public class ClientSynchronizer extends Thread{
    
    private static Socket sock;
    private File folder;
    private long previous_time;    

    
    private static PrintStream os;
    public static String ClientDirectory;
    public static int port;
    private String path;
    
    public ClientSynchronizer(String path, int port) throws IOException{
        
        folder= new File(path);
        ClientDirectory= path+"/";
        previous_time= currentTimeMillis();        
        this.port= port;        
        //sock = new Socket("localhost", port);
    }
    
    
    
    public void run(){     
        while (true) {    
            try {                
                File[] listOfFiles = folder.listFiles();
                previous_time= currentTimeMillis()- 5000;
                
                for (int i = 0; i < listOfFiles.length; i++) {                                        
                    
                    /*String temp= ClientDirectory+listOfFiles[i].getName();
                    Path p = Paths.get(temp);
                    BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);
                    long t=attr.creationTime().toMillis();
                    
                    System.out.println("File " + listOfFiles[i].getName()
                                +"Creation : "+t+" previous time "+previous_time);
                    (t>previous_time)
                    */
                    if(listOfFiles[i].isFile() &&(false||(listOfFiles[i].lastModified()>previous_time))) {
                        
                        String str= listOfFiles[i].getName();
                        
                        System.out.println("File " + str
                                +"last Modified : "+listOfFiles[i].lastModified());
                                                                     
                        sendFile(str);
                                                                  
                    }
                    else if(listOfFiles[i].isDirectory() && (listOfFiles[i].lastModified()>previous_time)) {
                         System.out.println("File " + listOfFiles[i].getName()
                                +"last Modified : "+listOfFiles[i].lastModified());
                    }
                }    
                
                Thread.sleep(5000);
                
            } catch (Exception e) {
                System.out.println("Inside ClientSynchronizer run() :"+e);
            }                        
        }
    }
    
    
    
//synchronized
    
    public synchronized static void sendFile(String fileName) {
        try {     
            sock = new Socket("localhost", port);
            
            os = new PrintStream(sock.getOutputStream());
            os.println("1");
            
            String FileToSend=ClientDirectory+fileName;
            System.out.println(FileToSend);

            File myFile = new File(FileToSend);
            byte[] mybytearray = new byte[(int) myFile.length()];

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            //bis.read(mybytearray, 0, mybytearray.length);

            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(mybytearray, 0, mybytearray.length);

            OutputStream os = sock.getOutputStream();

            //Sending file name and file size to the server
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(myFile.getName());
            dos.writeLong(mybytearray.length);
            dos.write(mybytearray, 0, mybytearray.length);            
            System.out.println("File "+fileName+" sent to Server.");                                                                        
            
            dos.flush();
            sock.close();
            
        } catch (Exception e) {
            System.err.println("File does not exist! "+e);
        }
    }
    
    
}
