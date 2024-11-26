package ServerCaps;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class Client {
	
	private static final int MULTICAST_PORT = 6969;
	private static final String MULTICAST_IP = "230.0.0.1";
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		try {
			MulticastSocket socket = new MulticastSocket(MULTICAST_PORT);
			
			socket.joinGroup(InetAddress.getByName(MULTICAST_IP));
			
			Thread receiverThread = new Thread(new Runnable() {
				public void run() {
					
					String message = new String();
					
					while(true) {
						
						try {
							
							byte[] buffer = new byte[1024];
							
							DatagramPacket packet = new DatagramPacket(buffer , buffer.length, InetAddress.getByName(MULTICAST_IP), MULTICAST_PORT);
							
							socket.receive(packet);
							
							String msg = new String(packet.getData(), 0, packet.getLength());
							
							
							if(!message.equals(msg)) System.out.println(msg);
							
							message = msg;
							
							if(msg.equals("EXIT")) break;
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
			
			
			Thread senderThread = new Thread(new Runnable() {
				public void run() {
					
					Scanner scanner = new Scanner(System.in);
					
					while(true) {
						try {
							String sendMsg = scanner.nextLine();
							
							DatagramPacket sendPacket = new DatagramPacket(sendMsg.getBytes(), sendMsg.getBytes().length, InetAddress.getByName(MULTICAST_IP), MULTICAST_PORT);
							
							socket.send(sendPacket);
							
							
							if(sendMsg.equals("exit")) break;
							
						} catch (IOException e) {
							e.printStackTrace();
						} 
					}
				}
			});
			
			
			
			senderThread.start();
			receiverThread.start();
			receiverThread.join();
			senderThread.join();
			
			
			socket.leaveGroup(InetAddress.getByName(MULTICAST_IP));
			socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
