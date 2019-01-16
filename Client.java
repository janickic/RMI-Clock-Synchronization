import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;  
import java.time.Instant;

public class Client {  
	private Client() {}  
	public static void main(String[] args) {  
		if (args.length < 1) {
			System.out.println("Please enter ip of host");
			return;
		}
		String serverHost = args[0];

		System.out.println("serverHost "+ serverHost);
		try {  
			// Getting the registry 
			Registry registry = LocateRegistry.getRegistry(serverHost); 

			// Looking up the registry for the remote object 
			SystemTime stub = (SystemTime) registry.lookup("SystemTime");

			// Get current time before calling the server to calculate RTT
			long start = Instant.now().toEpochMilli();

			// Calling the remote method using the obtained object 
			long serverTime = stub.getSystemTime();

			long end = Instant.now().toEpochMilli(); 
			//System.out.println("ServerTime "+ serverTime);

			// Calulate RTT
			long rtt = (end-start)/2;
			//System.out.println("rtt "+ rtt);

			// Calcuate clientTime to set the client clock with RTT delay
			long clientTime = serverTime+rtt;
			System.out.println("ClientTime "+ clientTime);

			// Set Client clock
			
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString()); 
			e.printStackTrace(); 
		} 
	} 
}