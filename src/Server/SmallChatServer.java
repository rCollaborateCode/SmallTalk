package server;

import java.net.ServerSocket;
import java.net.Socket;

public class SmallChatServer implements Runnable{

	private ServerSocket server = null;
	private Thread thread = null;
	private SmalltalkServer client = null;
	
	/*
	 * Constructor for SmallChatServer
	 * Parameters - port as an integer
	 * Binds to port and begins the server
	 */
	public SmallChatServer(int port){
		try{
			System.out.println("Binding to port " + port + ", please wait...");
			server = new ServerSocket(port);
			System.out.println("Server started: " + server);
			start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*
	 * Method that starts the thread
	 */
	private void start(){
		if (thread == null){
			thread = new Thread(this); 
			thread.start();
		}	
	}
	
	/*
	 * Method that stops the thread
	 */
	public void stop(){  
		if (thread != null){
		   thread.stop(); 
		   thread = null;
		}
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while(thread != null){
			try{
				System.out.println("Waiting for client...");
				addThread(server.accept());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	/*
	 * Adds a new client when needed
	 * Parameters - Socket
	 * Accepts client and starts their thread
	 */
	public void addThread(Socket socket){
		System.out.println("Accepting client...");
		client = new SmalltalkServer(this, socket);
		try{
			client.open();
			client.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
