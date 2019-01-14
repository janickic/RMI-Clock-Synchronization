import java.rmi.registry.Registry; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject; 
import java.net.InetAddress;

public class Server extends DefaultSystemTime { 
   public Server() {} 
   public static void main(String args[]) { 
      
      try { 
         // Instantiating the implementation class 
         DefaultSystemTime obj = new DefaultSystemTime(); 
    
         // Exporting the object of implementation class  
         // (here we are exporting the remote object to the stub) 
         SystemTime stub = (SystemTime) UnicastRemoteObject.exportObject(obj, 0);  
         
         // Looking up rmi registry
         Registry registry = LocateRegistry.getRegistry(); 
         
         // Binding the remote object (stub) in the registry 
         registry.bind("SystemTime", stub);  

         System.err.println("Server ready"); 
      } catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      } 
   } 
} 