import java.rmi.registry.Registry; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject; 
import java.net.InetAddress;
import java.rmi.Naming;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Server extends DefaultSystemTime { 
   public Server() {} 
   public static void main(String args[]) {  

      // Getting IPV4 address
      String ip = null;
      try {
         Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
         while (interfaces.hasMoreElements()) {
            NetworkInterface iface = interfaces.nextElement();
            // filters out 127.0.0.1 and inactive interfaces
            if (iface.isLoopback() || !iface.isUp())
                  continue;

            Enumeration<InetAddress> addresses = iface.getInetAddresses();
            while(addresses.hasMoreElements()) {
                  InetAddress addr = addresses.nextElement();
                  if (addr instanceof Inet6Address) continue;
                  ip = addr.getHostAddress();
                  // System.out.println(iface.getDisplayName() + " " + ip);
            }
         }
      } catch (Exception e) {
         throw new RuntimeException(e);
      }

      System.setProperty("java.rmi.server.hostname", ip);

      // Creating rmi registry
      try {
         LocateRegistry.createRegistry(1099);
      } catch (Exception e) {
         System.err.println("Failed to create registry: " + e.toString()); 
         e.printStackTrace(); 
      }
      String objPath = "//localhost:1099/SystemTime";

      try { 
         // Instantiating the implementation class 
         DefaultSystemTime obj = new DefaultSystemTime(); 
    
         // Exporting the object of implementation class  
         // (here we are exporting the remote object to the stub) 
         SystemTime stub = (SystemTime) UnicastRemoteObject.exportObject(obj, 0);  
         
         // Binding the remote object (stub) in the registry 
         Naming.bind(objPath, obj);  

         System.err.println("Server ready"); 
      } catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      } 
   } 
} 