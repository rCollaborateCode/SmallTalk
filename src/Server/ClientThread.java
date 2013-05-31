package server;

import java.io.DataInputStream;
import java.net.Socket;

import client.SmalltalkClient;


public class ClientThread extends Thread{
	private Socket socket = null;
	private SmalltalkClient client = null;
	private DataInputStream streamIn = null;
	
	/*
	 * Constructor for ClientThread
	 */
	public ClientThread(SmalltalkClient client, Socket socket){
		this.client = client;
		this.socket = socket;
		open();
		start();
	}
	
	/*
	 * Opens the stream in to receive information from the socket
	 */
	public void open(){
		try{
			streamIn = new DataInputStream(socket.getInputStream());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*
	 * Closes the stream
	 */
	public void close(){
		try{
			if(streamIn != null)
				streamIn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run(){
		while(true){
			try{
				System.out.println("running");
			}catch(Exception e){
				e.printStackTrace();
				try {
					client.stop();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
