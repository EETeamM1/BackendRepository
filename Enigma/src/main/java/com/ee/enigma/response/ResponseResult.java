package com.ee.enigma.response;

import org.json.simple.JSONObject;

public class ResponseResult {
	private String sessionToken;
	private int timeout;
	private String masterPassword;
	private JSONObject jsonObject=new JSONObject();
	
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
  public JSONObject getJsonObject()
  {
    return jsonObject;
  }
  public void setJsonObject(JSONObject jsonObject)
  {
    this.jsonObject = jsonObject;
  }
		
}
