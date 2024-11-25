package ServerCaps;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class Server {
	private static final int MULTICAST_PORT = 6969;
	private static final String MULTICAST_IP = "230.0.0.1";
	static String message = "Comunicazione Iniziata";
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		try {
			MulticastSocket socket = new MulticastSocket(MULTICAST_PORT);
			
			socket.joinGroup(InetAddress.getByName(MULTICAST_IP));
			
			
			
			
			Thread senderThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						byte[] msg = new byte[1024];
						while(true) {
							
							String c = new String(msg);
								
							if(!c.equals(message)) {
								
								msg = message.toUpperCase().getBytes();
								
								DatagramPacket packet = new DatagramPacket(msg, msg.length, InetAddress.getByName(MULTICAST_IP), MULTICAST_PORT);
											
								socket.send(packet);
																
								if(msg.equals("exit".toUpperCase().getBytes())) break;
							}
						}
					} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			
			Thread ReceiverThread = new Thread(new Runnable() {
				@Override
				public void run() {
					
					try {
						while(true) {
							byte[] buffer = new byte[1024];
							
							DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_IP), MULTICAST_PORT);
							
							socket.receive(packet);
							
							String msg = new String(packet.getData(), 0, packet.getLength());
							
							message = msg;
														
							if(msg.equals("exit")) break;
						}
							
					} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			
			ReceiverThread.start();
			senderThread.start();
			
			senderThread.join();
			ReceiverThread.join();
			
			socket.leaveGroup(InetAddress.getByName(MULTICAST_IP));
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
}
