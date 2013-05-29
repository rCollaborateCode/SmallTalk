/* 
 * SmallTalk Account Object
 * 
 * Tyler Barber 
 * Updated 2013-05-10
 * 
 * The purpose of this file is for the creation and modification
 * of SmallTalk accounts. A user can create an account, or use 
 * their existing account information in the Client to log in.
 * 
 * TODO LIST: 
 * 1) Add ability to change username and password
 * 2) Actually handle errors.
 * 
 * For existing account information see accounts file.
 */

package Server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class account{

	private String email;
	private String username;
	private String password;

	// public String lastLogin;

	/*
	 * Creates an Account object.
	 * Identifies its Username that was passed in by loginHandler or Client.
	 * Fetches the rest of account info from accounts file afterwards.
	 */

	public account() {

	}

	/*
	 * Used to create a new SmallTalk account.
	 * Parameters will be passed in from client.
	 * Identifies username, password and user identity.
	 * Afterwards, the account information is written to the accounts file.
	 */

	public void createUserAccount(String username, String password, String email) {
		
		this.email = email;
		this.username = username;
		this.password = password;

		saveAccount();
	}

	/*
	 * Used to populate the password and identity field for Account objects.
	 * If the username exists in the accounts file, then this method fetches
	 * the password and identity. 
	 * If the username does not exist in the accounts file, the password
	 * and the identity are left as null.
	 * This allows for the creation of new accounts.	
	 */

	public void fetchAccountInfo(String email) {

		this.email = email;
		File accounts = new java.io.File("accounts.txt");  

		try {
			Scanner accountFinder = new Scanner(accounts);
			while(accountFinder.hasNextLine()) {
				if (accountFinder.nextLine().equals("email: " + email)) {
					String userPassword = accountFinder.nextLine().toString();
					this.username = userPassword.replace("username: ", ""); 
					String userIdentity = accountFinder.nextLine().toString();
					this.password = userIdentity.replace("password: ", ""); 
				}
			}
			accountFinder.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Used to see if a username is taken.
	 * As of now this is not used locally. Client will use this later on
	 * in order to see if a username is free for use.
	 * Checks if password field is null, if so then the user account is not
	 * already in the accounts file.
	 */

	public boolean doesAccountExist(String email) {

		fetchAccountInfo(email);
		if(this.password != null) return true;
		else return false;
	}

	public boolean isUsernameTaken(String username) {

		File accounts = new java.io.File("accounts.txt");  

		try {
			Scanner accountFinder = new Scanner(accounts);
			while(accountFinder.hasNextLine()) {
				if (accountFinder.nextLine().equals("username: " + username)) {
					accountFinder.close();
					return true;
				}
			}
			accountFinder.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * Currently writes the users new account out to a plain text accounts file.
	 * Writes real name, username, and then password.
	 * TODO Write to some sort of database. 
	 * 		Encrypt stored user information.
	 * 		Return an actual error. 
	 */

	private void saveAccount() {

		try{ 
			FileWriter accountWriter = new FileWriter("accounts.txt", true);
			BufferedWriter out = new BufferedWriter(accountWriter);
			out.newLine();
			out.write("email: " + this.email);
			out.newLine();
			out.write("username: " + this.username);
			out.newLine();
			out.write("password: " + this.password);
			out.newLine();
			out.flush();
			out.close();
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}

	/*
	 * Used to output account information to a string.
	 * Will be used by the Client to display account information if requested.
	 */

	public String toString() {

		return "username: " + username + "\npassword: " + password
				+ "\nemail: " + email;
	}
	
	/*
	 * Used to send password information to other classes
	 * Parameters - none
	 * Returns - password as a String
	 * 
	 */
	
	public String getPassword(){
		return this.password;
	}
	
	/*
	 * 
	 * Used to encrypt account information
	 * Parameters - String that is to be encrypted
	 * Returns - The encrypted string in hex format
	 * 
	 */
	
	public String hashPassword(String pass) throws NoSuchAlgorithmException{
		MessageDigest mess = MessageDigest.getInstance("MD5");
		mess.update(pass.getBytes());
		byte digest[] = mess.digest();
		return toHexString(digest);
	}
	
	/*
	 * 
	 * Used to convert a byte array to hex
	 * Parameters - byte array
	 * Returns - String in hex format
	 * 
	 */
	
	private String toHexString(byte[] bytes) {
	    final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	    char[] hexChars = new char[bytes.length * 2];
	    int v;
	    for ( int j = 0; j < bytes.length; j++ ) {
	        v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
}
