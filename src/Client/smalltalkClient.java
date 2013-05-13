 package Client;
 
 public class smalltalkClient {
	
	
	static int clientPort;
	static int serverPort;
	
	public static void main(String[] args) {
		
		openLoginWindow(); // Opens login GUI
		
		/*
		 * Wait for user decision
		 * 	- Logging in
		 * 	- Creating new account
		 */
		
		connectToServer(); // Establishes a connection with stServer
		
	}
	
	public static void openLoginWindow() {
		
	}
	
	public static boolean connectToServer() {
		return true;
	}
 
 }
