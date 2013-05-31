package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Socket;

public class SmalltalkServer extends Thread {

	DatagramPacket pkt;
	private Socket socket = null;
	private SmallChatServer server = null;
	private int ID;
	private DataInputStream streamIn = null;
	private DataOutputStream streamOut = null;

	/*
	 * Constructor for smalltalkServer
	 * Creates server
	 */
	public SmalltalkServer(SmallChatServer server, Socket socket) {
		this.server = server;
		this.socket = socket;
		ID = socket.getPort();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		System.out.println("Server thread " + ID + " running...");
		while(true){
			try{
				System.out.println(streamIn.readUTF());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Sends a message
	 * Temporary method...must integrate with the Common package
	 */
	public void send(String msg){
		try{
			streamOut.writeUTF(msg);
			streamOut.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*
	 * Method for opening a new stream in
	 */
	public void open(){
		try{
			streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/*
	 * Method for closing the socket, streamIn, and streamOut
	 */
	public void close() throws IOException{
		if(socket != null)
			socket.close();
		if(streamIn != null)
			streamIn.close();
		if(streamOut != null)
			streamOut.close();
	}
}
