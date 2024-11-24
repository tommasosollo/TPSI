package NumeriPanettiere;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Client {
	
	public static final int MULTICAST_PORT = 6969;
	public static final String MULTICAST_IP = "230.0.0.1";
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		try {
			MulticastSocket socket = new MulticastSocket(MULTICAST_PORT);
			socket.joinGroup(InetAddress.getByName(MULTICAST_IP));
			
			while (true) {
				
				byte[] buffer = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_IP), MULTICAST_PORT);
				
				socket.receive(packet);
				
				String msg = new String(packet.getData(), 0, packet.getLength());
				
				System.out.println(msg);
				
				if(msg.equals("100")) break;
			}
			
			socket.leaveGroup(InetAddress.getByName(MULTICAST_IP));
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
