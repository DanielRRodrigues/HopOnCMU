package pt.ulisboa.tecnico.cmu.server;

public class Account {
	private String code;
	private String username;
	
	public Account(String code, String username) {
		this.code = code;
		this.username = username;
	}
	
	public String getCode() {
		return this.code;
	}
	public String getUsername() {
		return this.username;
	}

}
