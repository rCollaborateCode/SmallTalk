package Server;

public class accountHandler{
 
	public account loginAccount;

	public accountHandler() {

	}

	public boolean loginAccountHandler(String username, String password) {

		if(loginAccount.doesAccountExist(username)) {
			if(attemptLogin(password)) return true;
		}
		return false;
	}

	public boolean createAccountHandler(String username, String password, String email) {
		
		if(!loginAccount.doesAccountExist(username)) {
			loginAccount.createUserAccount(username, password, email);
			return true;
		}
		return false;
	}
	
	public account forgotAccountHandler(String email) {
		
		if(loginAccount.doesAccountExist(email)) {
			loginAccount.fetchAccountInfo(email);
			return loginAccount;
		}
		return null;
		
	}

	public boolean attemptLogin(String password) { 

		if (loginAccount.password.equals(password)) return true;
		else return false;
	}
}
