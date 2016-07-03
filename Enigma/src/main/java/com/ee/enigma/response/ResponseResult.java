package com.ee.enigma.response;


public class ResponseResult {
	private String sessionToken;
	private int timeout;
	private String masterPassword;

	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public String getMasterPassword() {
		return masterPassword;
	}
	public void setMasterPassword(String masterPassword) {
		this.masterPassword = masterPassword;
	}
		
}
