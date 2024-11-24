package NumeriPanettiere;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Server {
	
	private static final int MULTICAST_PORT = 6969;
	private static final String MULTICAST_IP = "230.0.0.1";
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		try {
			MulticastSocket socket = new MulticastSocket(MULTICAST_PORT);
			int n = 1;
			
			socket.joinGroup(InetAddress.getByName(MULTICAST_IP));
			
			while (true) {
				
				String msg = "serviamo il numero " + Integer.toString(n);
				
				DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName(MULTICAST_IP), MULTICAST_PORT);
				
				socket.send(packet);
				
				System.out.println(msg);
				
				if(msg.equals("serviamo il numero 100")) break;
				
				n+=1;
			}
			
			socket.leaveGroup(InetAddress.getByName(MULTICAST_IP));
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
