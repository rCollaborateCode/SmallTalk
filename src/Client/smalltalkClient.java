package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import server.ClientThread;


 public class SmalltalkClient implements Runnable{
	
	static int clientPort;
	static int serverPort = 4242; //Random port number
	static String hostName = "localhost"; //Temporary host name
	private Socket socket = null;
	private Thread thread = null;
	private DataInputStream  console = null;
	private DataOutputStream streamOut = null;
	private ClientThread client = null;
	
	
	public static void main(String[] args) {
		
		openLoginWindow(); // Opens login GUI
		
		/*
		 * Wait for user decision
		 * 	- Logging in
		 * 	- Creating new account
		 */
		SmalltalkClient client = new SmalltalkClient();
		
	}
	
	public static void openLoginWindow() {
		
	}
	
	/*
	 * Constructor for starting server
	 * Opens the socket at hostName and starts the server
	 */
	public SmalltalkClient(){
		System.out.println("Establishing connection...");
		try{
			socket = new Socket(hostName, serverPort);
			System.out.println("Connected to " + socket);
			start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*
	 * Method that starts the thread and makes a new client
	 */
	
	public void start() throws IOException{
		console = new DataInputStream(System.in);
		streamOut = new DataOutputStream(socket.getOutputStream());
		if (thread == null){
			client = new ClientThread(this, socket);
			thread = new Thread(this);                   
			thread.start();
		}
	}
	   
	/*
	 * Method that stops the thread and server 
	 */
	public void stop() throws IOException{  
		if(thread != null){
			thread.stop();  
			thread = null;
		}
		try{
			if(console != null)  
				console.close();
			if(streamOut != null)  
				streamOut.close();
			if(socket != null)  
				socket.close();
		}catch(Exception e){
			System.out.println("Error closing ..."); 
		}
		client.close();  
		client.stop();
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while(thread != null){
			try{
				 streamOut.writeUTF(console.readLine());
				 streamOut.flush();
			}catch(Exception e){
				e.printStackTrace();
				try {
					stop();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}
 
}
